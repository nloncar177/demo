# Koristi službeni Tomcat kao bazu
FROM tomcat:10.1.4

# Izbriši default aplikacije ako želiš čisti Tomcat
# RUN rm -rf /usr/local/tomcat/webapps/*


FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
RUN mvn clean package

# Faza 2: deploy na Tomcat
FROM tomcat:10.1
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /app/target/demo.war /usr/local/tomcat/webapps/ROOT.war



# Kopiraj svoj .war file kao ROOT.war (da se aplikacija otvori direktno)
#COPY /target/demo.war /usr/local/tomcat/webapps/ROOT.war

# Expose port (nije obavezan jer Render zna koristiti 10000 interni port)
EXPOSE 8080
