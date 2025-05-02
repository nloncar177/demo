# Koristi službeni Tomcat kao bazu
FROM tomcat:9.0

# Izbriši default aplikacije ako želiš čisti Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Kopiraj svoj .war fajl kao ROOT.war (da se aplikacija otvori direktno)
COPY target/demo.war /usr/local/tomcat/webapps/ROOT.war

# Expose port (nije obavezan jer Render zna koristiti 10000 interni port)
EXPOSE 8080
