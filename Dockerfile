FROM eclipse-temurin:17-jre-alpine

# Install required tools
RUN apk add --no-cache curl jq dos2unix

# Create working directory
WORKDIR /home/selenium-docker

# Copy files
COPY target/docker-resources/ ./
COPY runner.sh .

# Fix Windows line endings and permissions
RUN dos2unix runner.sh && chmod +x runner.sh
RUN chmod -R +x /home/selenium-docker

# Run script
ENTRYPOINT ["./runner.sh"]