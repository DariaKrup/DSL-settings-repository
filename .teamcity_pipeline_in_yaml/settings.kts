import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.pipelines.*
import jetbrains.buildServer.configs.kotlin.triggers.vcs
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

version = "2026.1"

project {

    vcsRoot(HttpsGithubComDariaKrupBookingApiPayconiqGitRefsHeadsMaster)

    buildType(BuildNumberReport)

    pipeline(DslSettingsRepositoryPipeline)
}

object BuildNumberReport : BuildType({
    name = "Build: number report"

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "echo %build.number%"
        }
    }
})

object HttpsGithubComDariaKrupBookingApiPayconiqGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/DariaKrup/BookingApiPayconiq.git#refs/heads/master"
    url = "https://github.com/DariaKrup/BookingApiPayconiq.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = ""
        password = "credentialsJSON:81db4f53-4a88-4d0b-8d8d-c0186aa7394a"
    }
})


object DslSettingsRepositoryPipeline : Pipeline({
    name = "DSL Settings Repository: pipeline"

    repositories {
        repository(DslContext.settingsRoot)
    }

    triggers {
        vcs {
        }
    }

    job(DslSettingsRepositoryPipeline_Job1)
})

object DslSettingsRepositoryPipeline_Job1 : Job({
    id("Job1")
    name = "Job 1"

    steps {
        script {
            scriptContent = "echo %system.teamcity.build.tempDir%"
        }
    }
})
