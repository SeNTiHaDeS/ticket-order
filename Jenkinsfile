#!groovy


node {

    //Etapa:Configurar environment

    stage 'Configurar environment'

    echo 'Configurando environment'
       def mvnHome = tool 'M3'
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

   // Etapa: Instalación
   stage 'Instalación'

   echo 'Instala el paquete generado en el repositorio maven'
   sh 'mvn install -Dmaven.test.skip=true'

   // ------------------------------------
   // -- ETAPA: Guardar
   // ------------------------------------
   stage 'Guardar'
   echo 'Archiva el paquete el paquete generado en Jenkins'
   step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar, **/target/*.war', fingerprint: true])

    // Etapa: Build Imagen
      stage 'Build Imagen'

      echo 'Buildear la imagen'
      dockerImage = docker.build("sentihades/prueba:latest")

        //Etapa: subir imagen
         stage 'Subir Imagen a DockerHub'
         echo 'Subir imagen a DockerHub'
         withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
         dockerImage.push()
         }

                 //  ETAPA: ejecutar contenedores

                 stage 'Ejecutando contenedores'

                 echo 'Ejecutando contenedores'
                 sh 'docker-compose down'
                 sh 'mvn clean install'
                 sh 'docker-compose up --build -d'
}