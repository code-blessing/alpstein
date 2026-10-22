plugins {
    id("org.flywaydb.flyway")
}

// Flyway reads FLYWAY_URL, FLYWAY_USER and FLYWAY_PASSWORD from the environment itself, and those
// win over whatever is configured here, so only the Gradle properties need wiring up.
fun dbSetting(gradleProperty: String, default: String): String =
    providers.gradleProperty(gradleProperty).getOrElse(default)

flyway {
    locations = arrayOf(
        "filesystem:../postgresql-dsl-schema/db/migration", // necessary for MEMBRUM_RELATUM and SILVA_OPTIONUM
        "filesystem:db/migration",
    )

    url = dbSetting("flyway.url", "jdbc:postgresql://localhost:5432/postgres")
    user = dbSetting("flyway.user", "postgres")
    password = dbSetting("flyway.password", "password")

    // The Sakila dump is loaded into the same `public` schema, so a development database is rarely
    // empty. Without a baseline Flyway refuses to migrate a populated schema that has no history
    // table yet. Baselining at 0 keeps V1 pending, which is what a database holding only Sakila
    // needs. A database whose schema was created by hand from the old reference DDL has to be
    // baselined at 1 instead, so the version stays overridable.
    baselineOnMigrate = true
    baselineVersion = dbSetting("flyway.baselineVersion", "0")
}
