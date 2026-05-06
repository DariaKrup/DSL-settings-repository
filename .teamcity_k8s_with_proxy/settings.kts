import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.kubernetesCloudProfile

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

    params {
        param("teamcity.internal.kubernetes.enableProxySettings", "true")
    }

    features {
        kubernetesCloudProfile {
            id = "kube-2"
            name = "GKE with proxy settings"
            terminateIdleMinutes = 30
            apiServerURL = "https://35.205.226.89"
            authStrategy = token {
                token = "credentialsJSON:90f62733-eaee-409e-ae69-9538673acd8c"
            }
            param("proxyServer", "http://34.53.237.245:8888")
            param("proxyPassword", "9551024Margo")
            param("proxyLogin", "dkrupkina")
        }
    }
}
