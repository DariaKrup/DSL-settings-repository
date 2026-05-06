import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

    vcsRoot(HttpsGithubComDariaKrupSimpleJavaMavenAppRefsHeadsMaster)
    vcsRoot(HttpsGithubComDariaKrupCommandLineRunnerRefsHeadsMaster)
    vcsRoot(HttpsGithubComDariaKrupAntProjectRefsHeadsMaster)

    buildType(BuildCmdWithDeletedPerfMon)
    buildType(AntBuildDisabledPerfMon)
    buildType(BuildJavaApp)
}

object AntBuildDisabledPerfMon : BuildType({
    name = "Ant build: disabled PerfMon"
    description = "PerfMon is manually disabled"

    vcs {
        root(HttpsGithubComDariaKrupAntProjectRefsHeadsMaster)
    }

    steps {
        ant {
            id = "Ant"
            mode = antFile {
            }
            targets = "build"
        }
    }

    features {
        perfmon {
            enabled = false
        }
    }
})

object BuildCmdWithDeletedPerfMon : BuildType({
    name = "Build: CMD with deleted PerfMon"

    vcs {
        root(HttpsGithubComDariaKrupCommandLineRunnerRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "sh echo.sh"
        }
    }
})

object BuildJavaApp : BuildType({
    name = "Build: Java App"
    description = "PerfMon is enabled by default"

    vcs {
        root(HttpsGithubComDariaKrupSimpleJavaMavenAppRefsHeadsMaster)
    }

    steps {
        maven {
            id = "Maven2"
            enabled = false
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
            jdkHome = "%env.JDK_21_0%"
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComDariaKrupAntProjectRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/DariaKrup/AntProject#refs/heads/master"
    url = "https://github.com/DariaKrup/AntProject"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_3a8d87760d0a08748e60351a6fe6a4bd:-1:2fa462de-142b-4454-8013-5c48818b7056"
    }
    param("tokenType", "refreshable")
})

object HttpsGithubComDariaKrupCommandLineRunnerRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/DariaKrup/command_line_runner#refs/heads/master"
    url = "https://github.com/DariaKrup/command_line_runner"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_3a8d87760d0a08748e60351a6fe6a4bd:-1:2eb7361b-33f9-4757-87e2-770774f5ce8c"
    }
    param("tokenType", "refreshable")
})

object HttpsGithubComDariaKrupSimpleJavaMavenAppRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/DariaKrup/simple-java-maven-app#refs/heads/master"
    url = "https://github.com/DariaKrup/simple-java-maven-app"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_3a8d87760d0a08748e60351a6fe6a4bd:-1:5d0fde06-ca50-41a5-b97e-6fee32ef5d03"
    }
    param("tokenType", "refreshable")
})
