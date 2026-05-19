import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.kubernetesCloudImage
import jetbrains.buildServer.configs.kotlin.kubernetesCloudProfile
import jetbrains.buildServer.configs.kotlin.projectFeatures.kubernetesConnection
import jetbrains.buildServer.configs.kotlin.projectFeatures.kubernetesExecutor

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

    buildType(CreateFile)

    params {
        param("teamcity.internal.kubernetes.enableProxySettings", "true")
    }

    features {
        kubernetesConnection {
            id = "PROJECT_EXT_21"
            name = "Kubernetes Connection"
            apiServerUrl = "https://35.205.226.89"
            authStrategy = token {
                token = "credentialsJSON:89baaffd-d821-4594-b317-29b793d3f94e"
            }
            param("proxyServer", "http://34.53.237.245:8888")
            param("secure:proxyPassword", "credentialsJSON:31f466c4-2353-4db1-b77d-d5569e73572d")
            param("proxyLogin", "dkrupkina")
        }
        kubernetesCloudImage {
            id = "PROJECT_EXT_22"
            profileId = "kube-2"
            agentPoolId = "-2"
            agentNamePrefix = "teamcity-agent-latest"
            podSpecification = runContainer {
                dockerImage = "jetbrains/teamcity-agent:latest"
            }
        }
        kubernetesConnection {
            id = "PROJECT_EXT_5"
            name = "Kubernetes Connection"
            apiServerUrl = "https://35.205.226.89"
            authStrategy = token {
                token = "credentialsJSON:89baaffd-d821-4594-b317-29b793d3f94e"
            }
            param("proxyServer", "http://34.53.237.245:8888")
            param("secure:proxyPassword", "credentialsJSON:31f466c4-2353-4db1-b77d-d5569e73572d")
            param("proxyLogin", "dkrupkina")
        }
        kubernetesExecutor {
            id = "PROJECT_EXT_8"
            connectionId = "PROJECT_EXT_5"
            profileName = "K8s Executor: GKE via proxy"
            buildsLimit = "2"
            serverURL = "http://52.51.163.108:8111"
            templateName = "teamcity-agent-simple"
        }
        kubernetesCloudProfile {
            id = "kube-2"
            name = "GKE with proxy settings"
            serverURL = "http://52.51.163.108:8111"
            terminateIdleMinutes = 30
            apiServerURL = "https://35.205.226.89"
            caCertData = "credentialsJSON:24f7696c-6577-4b00-a83b-d8dee7993ecd"
            authStrategy = token {
                token = "credentialsJSON:89baaffd-d821-4594-b317-29b793d3f94e"
            }
            param("proxyServer", "http://34.53.237.245:8888")
            param("secure:proxyPassword", "credentialsJSON:31f466c4-2353-4db1-b77d-d5569e73572d")
            param("proxyLogin", "dkrupkina")
        }
    }
}

object CreateFile : BuildType({
    name = "Create file"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """echo "Build: %build.number%" >> build.txt"""
        }
    }
})
