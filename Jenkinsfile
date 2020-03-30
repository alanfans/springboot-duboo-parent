pipeline {
  agent any
  stages {
    stage('build') {
      parallel {
        stage('build') {
          steps {
            sh 'mvn install clean'
          }
        }

        stage('') {
          steps {
            emailext(subject: 'build', body: 'build fail')
          }
        }

      }
    }

    stage('copy') {
      steps {
        sshPublisher(alwaysPublishFromMaster: true)
      }
    }

    stage('') {
      steps {
        archiveArtifacts(onlyIfSuccessful: true, artifacts: 'master')
      }
    }

  }
}