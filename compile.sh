$JAVA_HOME/bin/native-image -jar ./build/libs/demo-0.0.1-SNAPSHOT.jar app --no-fallback

# Note: the tracing agent parameter must come before the classpath and jar params on the command ine
$JAVA_HOME/bin/java -agentlib:native-image-agent=config-merge-dir=META-INF/native-image -jar ./build/libs/demo-0.0.1-SNAPSHOT.jar