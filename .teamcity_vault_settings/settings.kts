import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.projectFeatures.hashiCorpVaultConnection
import jetbrains.buildServer.configs.kotlin.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.remoteParameters.hashiCorpVaultParameter

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2026.1"

project {

    buildType(PublishFile)

    features {
        hashiCorpVaultConnection {
            id = "VaultConnection1"

            name = "HashiCorp Vault (Sub)"
            url = "https://localhost:8200"
            authMethod = appRole {
                roleId = "e0d9ef3e-a837-c70c-ea96-46e9870e6567"
                secretId = ""
            }
        }
    }
}

object PublishFile : BuildType({
    name = "Publish file"

    artifactRules = "test.txt"

    params {
        hashiCorpVaultParameter {
            name = "env.AWS_ACCESS_KEY_ID"
            vaultId = "VaultConnection1"
            query = "aws/data/access!/AWS_ACCESS_KEY_ID"
        }

        select("selectParameter", "",
            options = listOf("a1" to "1", "a2" to "2", "a3" to "3"))

        text("parameter", "text")
        text("second", "parameter")
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "test" >> test.txt"""
        }
    }
})

