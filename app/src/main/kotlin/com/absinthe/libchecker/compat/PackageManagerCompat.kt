@file:Suppress("DEPRECATION")

package com.absinthe.libchecker.compat

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import com.absinthe.libchecker.SystemServices
import com.absinthe.libchecker.utils.OsUtils

object PackageManagerCompat {
  val MATCH_DISABLED_COMPONENTS = if (OsUtils.atLeastN()) {
    PackageManager.MATCH_DISABLED_COMPONENTS
  } else {
    PackageManager.GET_DISABLED_COMPONENTS
  }

  val MATCH_UNINSTALLED_PACKAGES = if (OsUtils.atLeastN()) {
    PackageManager.MATCH_UNINSTALLED_PACKAGES
  } else {
    PackageManager.GET_UNINSTALLED_PACKAGES
  }

  fun getPackageInfo(packageName: String, flags: Int): PackageInfo {
    return if (OsUtils.atLeastT()) {
      SystemServices.packageManager.getPackageInfo(
        packageName,
        PackageManager.PackageInfoFlags.of(flags.toLong())
      )
    } else {
      SystemServices.packageManager.getPackageInfo(packageName, flags)
    }
  }

  fun getPackageArchiveInfo(archiveFilePath: String, flags: Int): PackageInfo? {
    return runCatching {
      if (OsUtils.atLeastT()) {
        SystemServices.packageManager.getPackageArchiveInfo(
          archiveFilePath,
          PackageManager.PackageInfoFlags.of(flags.toLong())
        )
      } else {
        SystemServices.packageManager.getPackageArchiveInfo(archiveFilePath, flags)
      }
    }.getOrNull()
  }

  fun getApplicationInfo(packageName: String, flags: Int): ApplicationInfo {
    return if (OsUtils.atLeastT()) {
      SystemServices.packageManager.getApplicationInfo(
        packageName,
        PackageManager.ApplicationInfoFlags.of(flags.toLong())
      )
    } else {
      SystemServices.packageManager.getApplicationInfo(packageName, flags)
    }
  }

  fun getInstalledPackages(flags: Int): List<PackageInfo> {
    return if (OsUtils.atLeastT()) {
      SystemServices.packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
      SystemServices.packageManager.getInstalledPackages(flags)
    }
  }

  fun queryIntentActivities(intent: Intent, flags: Int): List<ResolveInfo> {
    return if (OsUtils.atLeastT()) {
      SystemServices.packageManager.queryIntentActivities(
        intent,
        PackageManager.ResolveInfoFlags.of(flags.toLong())
      )
    } else {
      SystemServices.packageManager.queryIntentActivities(intent, flags)
    }
  }
}
