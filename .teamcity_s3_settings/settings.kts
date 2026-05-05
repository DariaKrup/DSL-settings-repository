import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.projectFeatures.s3Storage

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

version = "2025.11"

project {

    buildType(PublishFile)

    features {
        activeStorage {
            id = "PROJECT_EXT_7"
            activeStorageID = "PROJECT_EXT_6"
        }
    }

    subProject(BucketInAnotherRegion)
}

object PublishFile : BuildType({
    name = "Publish file"

    artifactRules = "test.txt"

    params {
        param("storage.s3.forceVirtualHostAddressing", "false")
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "test" >> test.txt"""
        }
    }
})


object BucketInAnotherRegion : Project({
    name = "Bucket in another region"

    buildType(BucketInAnotherRegion_Publish)

    features {
        s3Storage {
            id = "PROJECT_EXT_10"
            storageName = "Bucket in Paris"
            bucketName = "tc-artifacts-dkrupkina-bucket"
            awsEnvironment = default {
                awsRegionName = "eu-west-3"
            }
            connectionId = "AmazonWebServicesAws"
            forceVirtualHostAddressing = false
        }
        activeStorage {
            id = "PROJECT_EXT_11"
            activeStorageID = "PROJECT_EXT_10"
        }
    }
})

object BucketInAnotherRegion_Publish : BuildType({
    name = "Publish"

    artifactRules = "text.txt"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "text" >> text.txt"""
        }
    }
})
