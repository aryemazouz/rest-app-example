Usage:

java -jar [JAVA_OPTS] <JAR_PATH> [APPLICATION_JSON_CONF_FILE] [ARGS]

# The application will try to use the first argument after the Jar path as the JSON configuration file path (vms & fw_rules).
  If the file doesn't exists or this is invalid file path the application will try to read the JSON configuration file path from 'rest.app.conf' property.
  If none of these exists the application will fail to start!!!!

Property Name          | Environment Variable Name         | Default Value
-----------------------+-----------------------------------+---------------
server.port             SERVER_PORT                         80
rest.app.cache.size     REST_APP_CASH_SIZE                  5000
rest.app.conf           REST_APP_CONF


~ Each property can be set from command line argument by using --<PROPERTY_NAME>=<VALUE> or set the relevant environment variable

# You can use your own application.properties file and redirect spring to use it by using (java options) -Dspring.config.location="FULL FILE PATH"
# You can use your own logback.xml by using (java options) -Dlogging.config="FULL_PATH/logback.xml"


Limitations:
# No "pretty / secure" error handling


Running Example:
java -jar -Dlogging.config="my_path/logback.xml" rest-app-1.0-SNAPSHOT.jar "./input-2.json" --server.port=8080