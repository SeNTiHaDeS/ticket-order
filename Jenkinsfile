#!groovy
node {

   //Etapa:Configurar environment

   stage 'Configurar environment'
   echo 'Configurando environment'
   def mvnHome = tool 'microservicios'
   env.PATH = "${mvnHome}/bin:${env.PATH}"
   echo "var mvnHome='${mvnHome}'"
   echo "var env.PATH='${env.PATH}'"

   // Etapa: Compilar aplicación

   stage 'Compilar Aplicación'
   echo 'Descargando código'
   sh 'rm -rf *'
   checkout scm
   echo 'Compilando aplicación'
   sh 'mvn clean compile'


   // Etapa: Integración sonarqube

   stage('SonarQube Analysis') {
      environment {
         SONAR_LOGIN = '64218da0ff112ed86195d2c43a8260b0bc5db7e1'
      }
      def scannerHome = tool 'sonar'
      withSonarQubeEnv('sonar') {
         sh "${scannerHome}/bin/sonar-scanner \
      -Dsonar.login=$SONAR_LOGIN \
      -Dsonar.projectKey=my-project \
      -Dsonar.sources=src \
      -Dsonar.host.url=http://localhost:9000 \
      -Dsonar.login=$SONAR_LOGIN"
      }
   }


   // Etapa: Test

   stage 'Test'
   echo 'Ejecutando tests'
   try{
      sh 'mvn verify'
      step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
   }catch(err) {
      step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
      if (currentBuild.result == 'UNSTABLE')
         currentBuild.result = 'FAILURE'
      throw err
   }

   // Etapa: Instalar y guardar JAR

   stage 'Instalar y guardar JAR'
   echo 'Instala el paquete generado en el repositorio maven'
   sh 'mvn install -Dmaven.test.skip=true'
   step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar, **/target/*.war', fingerprint: true])


   // Etapa: Build Imagen

   stage 'Build Imagen y subir a DockerHub'
   echo 'Buildear la imagen'
   dockerImage = docker.build("ggrande/ticket-order:latest")
   echo 'Subir imagen a DockerHub'
   withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
      dockerImage.push()
   }

   //  ETAPA: ejecutar contenedores

   stage 'Ejecutando contenedores'
   echo 'Ejecutando contenedores'
   sh 'docker-compose down'
   sh 'docker-compose up --build -d'
}