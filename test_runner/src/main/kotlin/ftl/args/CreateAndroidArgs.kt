package ftl.args

import ftl.args.yml.AppTestPair
import ftl.config.AndroidConfig
import ftl.config.android.AndroidFlankConfig
import ftl.config.android.AndroidGcloudConfig

fun createAndroidArgs(
    config: AndroidConfig? = null,
    gcloud: AndroidGcloudConfig = config!!.platform.gcloud,
    flank: AndroidFlankConfig = config!!.platform.flank,
    commonArgs: CommonArgs = config!!.prepareAndroidCommonConfig()
) = AndroidArgs(
    commonArgs = commonArgs,
    // gcloud
    appApk = gcloud.app?.normalizeFilePath(),
    testApk = gcloud.test?.normalizeFilePath(),
    useOrchestrator = gcloud.useOrchestrator!!,
    testTargets = gcloud.testTargets!!.filterNotNull(),
    testRunnerClass = gcloud.testRunnerClass,
    roboDirectives = gcloud.roboDirectives!!.parseRoboDirectives(),
    performanceMetrics = gcloud.performanceMetrics!!,
    otherFiles = gcloud.otherFiles!!.mapValues { (_, path) -> path.normalizeFilePath() },
    numUniformShards = gcloud.numUniformShards,
    environmentVariables = gcloud.environmentVariables!!,
    directoriesToPull = gcloud.directoriesToPull!!,
    autoGoogleLogin = gcloud.autoGoogleLogin!!,
    additionalApks = gcloud.additionalApks!!.map { it.normalizeFilePath() },
    roboScript = gcloud.roboScript?.normalizeFilePath(),

    // flank
    additionalAppTestApks = flank.additionalAppTestApks?.map { (app, test) ->
        AppTestPair(
            app = app?.normalizeFilePath(),
            test = test.normalizeFilePath()
        )
    } ?: emptyList(),
    useLegacyJUnitResult = flank.useLegacyJUnitResult!!
)
