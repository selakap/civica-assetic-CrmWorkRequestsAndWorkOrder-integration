FROM public.ecr.aws/amazoncorretto/amazoncorretto:17 as base

# Configure the build environment
FROM base as build
RUN yum install -y maven
WORKDIR /src

# Cache and copy dependencies
ADD pom.xml .
RUN mvn dependency:go-offline dependency:copy-dependencies

# Compile the function
ADD . .
RUN mvn package -DskipTests

# Copy the function artifact and dependencies onto a clean base
FROM base

WORKDIR /function

COPY --from=build /src/target/dependency/*.jar ./
COPY --from=build /src/target/*.jar ./
COPY --from=build /src/target/classes/* ./

RUN ls -l ./




# Set runtime interface client as default command for the container runtime
ENTRYPOINT ["/usr/bin/java", "-cp", "./*", "com.amazonaws.services.lambda.runtime.api.client.AWSLambda"]
# Pass the name of the function handler as an argument to the runtime
CMD ["org.symphony3.smartglue.integration.murrindindi.handlers.AuthorityToAsseticHandler::handleRequest"]
