# Koristi službeni Tomcat kao bazu
FROM tomcat:10.1.4

# Izbriši default aplikacije ako želiš čisti Tomcat
# RUN rm -rf /usr/local/tomcat/webapps/*

#RUN mvn clean package
#RUN rm -rf /usr/local/tomcat/webapps/*
#COPY --from=build /app/target/demo.war /usr/local/tomcat/webapps/ROOT.war

# Kopiraj svoj .war file kao ROOT.war (da se aplikacija otvori direktno)
# COPY target/demo.war /usr/local/tomcat/webapps/ROOT.war

# Expose port (nije obavezan jer Render zna koristiti 10000 interni port)
EXPOSE 8080
