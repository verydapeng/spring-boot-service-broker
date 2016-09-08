# spring-boot-service-broker
Simple cloud foundry service broker written in springboot


# to deploy 

```mvn package && cf push```

```cf create-service-broker myservice-broker user pass https://broker-{aaa-bbb}.cfapps.io --space-scoped```

```cf cs myservice small myservice-instance-1```

```cf bs {my-app} myservice-instance-1```

