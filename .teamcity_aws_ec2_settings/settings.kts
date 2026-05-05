import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.amazonEC2CloudProfile
import jetbrains.buildServer.configs.kotlin.projectFeatures.awsConnection

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

    features {
        awsConnection {
            id = "AWSEC2DSLWithPoolProject_LocalAmazonWebServicesAws"
            name = "Local Amazon Web Services (AWS)"
            regionName = "eu-west-1"
            credentialsType = static {
                accessKeyId = "AKIA5JH2VERVI62P5XDY"
                secretAccessKey = "credentialsJSON:6a713cb0-6964-432e-a90f-1fe257ae3ad2"
            }
            allowInSubProjects = true
            allowInBuilds = true
            stsEndpoint = "https://sts.eu-west-1.amazonaws.com"
        }
        amazonEC2CloudProfile {
            id = "amazon-1"
            name = "AWS EC2 profile"
            terminateIdleMinutes = 30
            region = AmazonEC2CloudProfile.Regions.EU_WEST_DUBLIN
            awsConnectionId = "AWSEC2DSLWithPoolProject_LocalAmazonWebServicesAws"
            maxInstancesCount = 3
        }
        amazonEC2CloudImage {
            id = "PROJECT_EXT_AWS"
            profileId = "amazon-1"
            agentPoolId = "-2023"
            imagePriority = 3
            name = "Instance Image"
            vpcSubnetId = "subnet-0c23f411b0800b216"
            keyPairName = "daria.krupkina"
            instanceType = "t2.medium"
            securityGroups = listOf("sg-072d8bfa0626ea2a6")
            source = Source("i-0aa8f308327fd1bc1")
        }
        amazonEC2CloudImage {
            id = "PROJECT_EXT_AWS"
            profileId = "amazon-1"
            agentPoolId = "1"
            imagePriority = 3
            name = "Instance Image"
            vpcSubnetId = "subnet-0c23f411b0800b216"
            keyPairName = "daria.krupkina"
            instanceType = "t2.medium"
            securityGroups = listOf("sg-072d8bfa0626ea2a6")
            source = Source("i-0aa8f308327fd1bc1")
        }
    }
}
