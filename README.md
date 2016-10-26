# spring-boot-service-broker
Simple cloud foundry service broker written in springboot


# to deploy 

Deploy the broker 
```mvn package && cf push```

Register the broker 
```cf create-service-broker myservice-broker user pass http://{COPY_BROKER_URL_HERE}.cfapps.io/ --space-scoped```

Get the service name 
```cf m```

Create service instance
```cf cs myservice-{COPY_UUID} small myservice-instance-1```

Bind service instance
```cf bs {my-app} myservice-instance-1```

