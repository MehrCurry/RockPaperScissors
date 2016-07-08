node('graphviz') {
    env.PATH = "${tool 'Maven3'}/bin:${env.PATH}"
    env.JAVA_HOME = "${tool 'Java8'}"

    stage 'Build and Test'
    checkout scm
    sh 'mvn -B -Pcoverage clean verify package -Dbuild.number=${BUILD_NUMBER}'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])

    stage 'Doc'
    sh 'mvn -B -Pdoc javadoc:javadoc'
    step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs', keepAll: false])

    stage 'Sonar'
    sh 'mvn -B sonar:sonar -Dsonar.host.url=$SONAR_URL'

    stage 'Docker Build'
    sh 'mvn -B docker:build'
}
