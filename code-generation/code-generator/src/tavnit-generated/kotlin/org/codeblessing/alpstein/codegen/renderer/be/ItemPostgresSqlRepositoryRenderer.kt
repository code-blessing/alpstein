/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemPostgresSqlRepositoryRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `PostgresSqlSilvaOptionumRepository.kt`
 * - path: `org/codeblessing/alpstein/server/persistence/silvaoptionum/PostgresSqlSilvaOptionumRepository.kt`
 */
object ItemPostgresSqlRepositoryRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.persistence.${model.itemName.lowerCase}
          |
          |import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
          |import org.springframework.context.annotation.Primary
          |import org.springframework.jdbc.core.RowMapper
          |import org.springframework.jdbc.core.simple.JdbcClient
          |import org.springframework.stereotype.Repository
          |import org.codeblessing.alpstein.server.persistence.postgres.columnValue
          |import org.codeblessing.alpstein.server.persistence.postgres.escapeForLikePattern
          |import org.codeblessing.alpstein.server.persistence.postgres.paramValue
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}ByIdsCriteriaBO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}SearchCriteriaBO
          |import org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}.${model.itemName.pascalCase}Repository
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |""" } else { """""" } }
          |private const val TABLE_NAME = "${model.table.tableName}"
          |private const val PRIMARY_KEY_COLUMN_NAME = "${model.table.primaryKeyColumn.columnName}"
          |/**
          | * PostgreSQL implementation of the [${model.itemName.pascalCase}Repository] port, storing every
          | * [${model.itemName.pascalCase}BO] aggregate as one row of the ${model.table.tableName} table.
          | *
          | * Attributes without a flat relational representation — nested items and lists of them — are
          | * stored as `jsonb`; every other list gets an array column of its element type, and every
          | * single value its own typed column.
          | *
          | * Only active when `org.codeblessing.alpstein.persistence.type=postgres`; it then takes precedence over the
          | * [org.codeblessing.alpstein.server.persistence.${model.itemName.lowerCase}.InMemory${model.itemName.pascalCase}Repository].
          | */
          |@Repository
          |@Primary
          |@ConditionalOnProperty(name = ["org.codeblessing.alpstein.persistence.type"], havingValue = "postgres")
          |class PostgresSql${model.itemName.pascalCase}Repository(
          |    private val jdbcClient: JdbcClient,
          |) : ${model.itemName.pascalCase}Repository {
          |
          |
          |    override fun findAll(): List<${model.itemName.pascalCase}BO> =
          |        jdbcClient.sql("${"$"}selectColumns ORDER BY ${"$"}PRIMARY_KEY_COLUMN_NAME").query(rowMapper).list()
          |
          |    override fun findById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}): ${model.itemName.pascalCase}BO? =
          |        jdbcClient.sql("${"$"}selectColumns WHERE ${"$"}PRIMARY_KEY_COLUMN_NAME = :primaryKeyValue")
          |            .param("primaryKeyValue", ${model.primaryKeyAttribute.attributeName.camelCase})
          |            .query(rowMapper)
          |            .optional()
          |            .orElse(null)
          |
          |    override fun findByIds(criteria: ${model.itemName.pascalCase}ByIdsCriteriaBO): List<${model.itemName.pascalCase}BO> {
          |        if (criteria.${model.primaryKeyAttribute.attributeName.camelCase}List.isEmpty()) {
          |            return emptyList()
          |        }
          |        val found = jdbcClient.sql("${"$"}selectColumns WHERE ${"$"}PRIMARY_KEY_COLUMN_NAME IN (:primaryKeyValues)")
          |            .param("primaryKeyValues", criteria.${model.primaryKeyAttribute.attributeName.camelCase}List)
          |            .query(rowMapper)
          |            .list()
          |            .associateBy { it.${model.primaryKeyAttribute.attributeName.camelCase} }
          |        return criteria.${model.primaryKeyAttribute.attributeName.camelCase}List.mapNotNull { found[it] }
          |    }
          |
          |    override fun search(searchCriteria: ${model.itemName.pascalCase}SearchCriteriaBO): List<${model.itemName.pascalCase}BO> =
          |        jdbcClient.sql("${"$"}selectColumns WHERE ${"$"}TABLE_NAME::text ILIKE :query ESCAPE '\\' ORDER BY ${"$"}PRIMARY_KEY_COLUMN_NAME")
          |            .param("query", "%${"$"}{searchCriteria.query.escapeForLikePattern()}%")
          |            .query(rowMapper)
          |            .list()
          |
          |    override fun create(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO {
          |        val createdPrimaryKey = jdbcClient.sql(insertStatement)
          |${ model.table.columnsWithoutPrimaryKey.joinToString("") { column ->  """            .param("${column.attributeName.camelCase}", paramValue(${model.itemName.camelCase}.${column.attributeName.camelCase}))
              |""" } }            .query(RowMapper { resultSet, _ -> resultSet.columnValue<${model.primaryKeyAttribute.kotlinAttributeType}>(PRIMARY_KEY_COLUMN_NAME) })
          |            .single()
          |        return ${model.itemName.camelCase}.copy(${model.primaryKeyAttribute.attributeName.camelCase} = createdPrimaryKey)
          |    }
          |
          |    override fun update(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO {
          |        val updatedRows = jdbcClient.sql(updateStatement)
          |${ model.attributes.joinToString("") { attribute ->  """            .param("${attribute.attributeName.camelCase}", paramValue(${model.itemName.camelCase}.${attribute.attributeName.camelCase}))
              |""" } }            .update()
          |        check(updatedRows == 1) {
          |            "No ${model.itemName.pascalCase} is stored under the primary key ${"$"}{${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}}, so there is nothing to update."
          |        }
          |        return ${model.itemName.camelCase}
          |    }
          |
          |    override fun deleteById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}) {
          |        jdbcClient.sql("DELETE FROM ${"$"}TABLE_NAME WHERE ${"$"}PRIMARY_KEY_COLUMN_NAME = :indexValue")
          |            .param("indexValue", ${model.primaryKeyAttribute.attributeName.camelCase})
          |            .update()
          |    }
          |
          |    private val rowMapper = RowMapper { resultSet, _ ->
          |        ${model.itemName.pascalCase}BO(
          |${ model.attributes.joinToString("") { attribute ->  """            ${attribute.attributeName.camelCase} = resultSet.columnValue("${attribute.column.columnName}"),
              |""" } }        )
          |    }
          |
          |    private val selectColumns: String
          |        get() {
          |            // because we need kotlin comments between the lines, we can not use kotlins multiline-comments
          |            val sb = StringBuilder()
          |            sb.appendLine("SELECT")
          |${ model.table.columnsWithoutPrimaryKey.joinToString("") { column ->  """            sb.appendLine("    ${column.columnName},")
              |""" } }            // the primary key is rendered last, so that every looped line above can carry a comma
          |            sb.appendLine("    ${"$"}PRIMARY_KEY_COLUMN_NAME")
          |            sb.appendLine("FROM ${"$"}TABLE_NAME")
          |            return sb.toString()
          |    }
          |    private val insertStatement: String
          |        get() {
          |            // because we need kotlin comments between the lines, we can not use kotlin multiline-comments
          |            val sb = StringBuilder()
          |            sb.appendLine("INSERT INTO ${"$"}TABLE_NAME (")
          |${ model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    ${column.columnName},")
              |""" } }${ model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    ${column.columnName},")
              |""" } }            sb.appendLine("    ${"$"}PRIMARY_KEY_COLUMN_NAME")
          |            sb.appendLine(") VALUES (")
          |${ model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    :${column.attributeName.camelCase},")
              |""" } }${ model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    CAST(:${column.attributeName.camelCase} AS ${column.sqlTypeName}),")
              |""" } }            // DEFAULT applies whatever hands out the key of this table, a sequence or a random uuid
          |            sb.appendLine("    DEFAULT")
          |            sb.appendLine(")")
          |            sb.appendLine("RETURNING ${"$"}PRIMARY_KEY_COLUMN_NAME")
          |            return sb.toString()
          |    }
          |    private val updateStatement: String
          |        get() {
          |            // because we need kotlin comments between the lines, we can not use kotlin multiline-comments
          |            val sb = StringBuilder()
          |            sb.appendLine("UPDATE ${"$"}TABLE_NAME SET")
          |${ model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    ${column.columnName} = :${column.attributeName.camelCase},")
              |""" } }${ model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }.joinToString("") { column ->  """            sb.appendLine("    ${column.columnName} = CAST(:${column.attributeName.camelCase} AS ${column.sqlTypeName}),")
              |""" } }            // assigning the key to itself is a no-op and keeps the last line free of a comma
          |            sb.appendLine("    ${"$"}PRIMARY_KEY_COLUMN_NAME = :${model.primaryKeyAttribute.attributeName.camelCase}")
          |            sb.appendLine("WHERE ${"$"}PRIMARY_KEY_COLUMN_NAME = :${model.primaryKeyAttribute.attributeName.camelCase}")
          |            return sb.toString()
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/persistence/${model.itemName.lowerCase}/PostgresSql${model.itemName.pascalCase}Repository.kt"
    }
}