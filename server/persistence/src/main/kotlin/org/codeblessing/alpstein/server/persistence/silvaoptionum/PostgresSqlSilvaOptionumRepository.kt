/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemPostgresSqlRepositoryRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.kotlinAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.persistence.silvaoptionum

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.JdbcClient
import org.springframework.stereotype.Repository
import org.codeblessing.alpstein.server.persistence.postgres.columnValue
import org.codeblessing.alpstein.server.persistence.postgres.escapeForLikePattern
import org.codeblessing.alpstein.server.persistence.postgres.paramValue
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumByIdsCriteriaBO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumSearchCriteriaBO
import org.codeblessing.alpstein.server.service.silvaoptionum.SilvaOptionumRepository
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */

/* @tt{{{
    @replace-value-by-expression
        [ searchValue="SILVA_OPTIONUM" replaceByExpression="model.table.tableName" ]
        [ searchValue="INDEX_UNICUS" replaceByExpression="model.table.primaryKeyColumn.columnName" ]
}}}@ */
private const val TABLE_NAME = "SILVA_OPTIONUM"
private const val PRIMARY_KEY_COLUMN_NAME = "INDEX_UNICUS"
/**
 * PostgreSQL implementation of the [SilvaOptionumRepository] port, storing every
 * [SilvaOptionumBO] aggregate as one row of the SILVA_OPTIONUM table.
 *
 * Attributes without a flat relational representation — nested items and lists of them — are
 * stored as `jsonb`; every other list gets an array column of its element type, and every
 * single value its own typed column.
 *
 * Only active when `org.codeblessing.alpstein.persistence.type=postgres`; it then takes precedence over the
 * [org.codeblessing.alpstein.server.persistence.silvaoptionum.InMemorySilvaOptionumRepository].
 */
/* @tt{{{   @end-replace-value-by-expression  }}}@ */
@Repository
@Primary
@ConditionalOnProperty(name = ["org.codeblessing.alpstein.persistence.type"], havingValue = "postgres")
class PostgresSqlSilvaOptionumRepository(
    private val jdbcClient: JdbcClient,
) : SilvaOptionumRepository {


    override fun findAll(): List<SilvaOptionumBO> =
        jdbcClient.sql("$selectColumns ORDER BY $PRIMARY_KEY_COLUMN_NAME").query(rowMapper).list()

    override fun findById(indexUnicus: UUID): SilvaOptionumBO? =
        jdbcClient.sql("$selectColumns WHERE $PRIMARY_KEY_COLUMN_NAME = :primaryKeyValue")
            .param("primaryKeyValue", indexUnicus)
            .query(rowMapper)
            .optional()
            .orElse(null)

    override fun findByIds(criteria: SilvaOptionumByIdsCriteriaBO): List<SilvaOptionumBO> {
        if (criteria.indexUnicusList.isEmpty()) {
            return emptyList()
        }
        val found = jdbcClient.sql("$selectColumns WHERE $PRIMARY_KEY_COLUMN_NAME IN (:primaryKeyValues)")
            .param("primaryKeyValues", criteria.indexUnicusList)
            .query(rowMapper)
            .list()
            .associateBy { it.indexUnicus }
        return criteria.indexUnicusList.mapNotNull { found[it] }
    }

    override fun search(searchCriteria: SilvaOptionumSearchCriteriaBO): List<SilvaOptionumBO> =
        jdbcClient.sql("$selectColumns WHERE $TABLE_NAME::text ILIKE :query ESCAPE '\\' ORDER BY $PRIMARY_KEY_COLUMN_NAME")
            .param("query", "%${searchCriteria.query.escapeForLikePattern()}%")
            .query(rowMapper)
            .list()

    override fun create(silvaOptionum: SilvaOptionumBO): SilvaOptionumBO {
        val createdPrimaryKey = jdbcClient.sql(insertStatement)
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="campusTextusObligatorius" replaceByExpression="column.attributeName.camelCase" ]
            }}}@ */
            .param("campusTextusObligatorius", paramValue(silvaOptionum.campusTextusObligatorius))
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            .param("campusTextusOptionalis", paramValue(silvaOptionum.campusTextusOptionalis))
            .param("appellatio", paramValue(silvaOptionum.appellatio))
            .param("articulusInteriorSingularis", paramValue(silvaOptionum.articulusInteriorSingularis))
            .param("articulusInteriorIteratus", paramValue(silvaOptionum.articulusInteriorIteratus))
            .param("articulusInteriorSingularisOptionalis", paramValue(silvaOptionum.articulusInteriorSingularisOptionalis))
            .param("articulusInteriorOptionalisIteratus", paramValue(silvaOptionum.articulusInteriorOptionalisIteratus))
            .param("appellatioOptionalisIteratus", paramValue(silvaOptionum.appellatioOptionalisIteratus))
            .param("campusDiei", paramValue(silvaOptionum.campusDiei))
            .param("campusBivalens", paramValue(silvaOptionum.campusBivalens))
            .param("campusNumerorum", paramValue(silvaOptionum.campusNumerorum))
            .param("iteratioSimpliciumTextuum", paramValue(silvaOptionum.iteratioSimpliciumTextuum))
            .param("relatioAdEntitatemOptionalisIteratus", paramValue(silvaOptionum.relatioAdEntitatemOptionalisIteratus))
            .param("relatioAdEntitatemOptionalis", paramValue(silvaOptionum.relatioAdEntitatemOptionalis))
            /* @tt{{{   @end-ignore-text  }}}@ */
            .query(RowMapper { resultSet, _ -> resultSet.columnValue<UUID>(PRIMARY_KEY_COLUMN_NAME) })
            .single()
        return silvaOptionum.copy(indexUnicus = createdPrimaryKey)
    }

    override fun update(silvaOptionum: SilvaOptionumBO): SilvaOptionumBO {
        val updatedRows = jdbcClient.sql(updateStatement)
            /* @tt{{{
                @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
                @replace-value-by-expression
                    [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
            }}}@ */
            .param("campusTextusObligatorius", paramValue(silvaOptionum.campusTextusObligatorius))
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            .param("indexUnicus", paramValue(silvaOptionum.indexUnicus))
            .param("campusTextusOptionalis", paramValue(silvaOptionum.campusTextusOptionalis))
            .param("appellatio", paramValue(silvaOptionum.appellatio))
            .param("articulusInteriorSingularis", paramValue(silvaOptionum.articulusInteriorSingularis))
            .param("articulusInteriorIteratus", paramValue(silvaOptionum.articulusInteriorIteratus))
            .param("articulusInteriorSingularisOptionalis", paramValue(silvaOptionum.articulusInteriorSingularisOptionalis))
            .param("articulusInteriorOptionalisIteratus", paramValue(silvaOptionum.articulusInteriorOptionalisIteratus))
            .param("appellatioOptionalisIteratus", paramValue(silvaOptionum.appellatioOptionalisIteratus))
            .param("campusDiei", paramValue(silvaOptionum.campusDiei))
            .param("campusBivalens", paramValue(silvaOptionum.campusBivalens))
            .param("campusNumerorum", paramValue(silvaOptionum.campusNumerorum))
            .param("iteratioSimpliciumTextuum", paramValue(silvaOptionum.iteratioSimpliciumTextuum))
            .param("relatioAdEntitatemOptionalisIteratus", paramValue(silvaOptionum.relatioAdEntitatemOptionalisIteratus))
            .param("relatioAdEntitatemOptionalis", paramValue(silvaOptionum.relatioAdEntitatemOptionalis))
            /* @tt{{{   @end-ignore-text  }}}@ */
            .update()
        check(updatedRows == 1) {
            "No SilvaOptionum is stored under the primary key ${silvaOptionum.indexUnicus}, so there is nothing to update."
        }
        return silvaOptionum
    }

    override fun deleteById(indexUnicus: UUID) {
        jdbcClient.sql("DELETE FROM $TABLE_NAME WHERE $PRIMARY_KEY_COLUMN_NAME = :indexValue")
            .param("indexValue", indexUnicus)
            .update()
    }

    private val rowMapper = RowMapper { resultSet, _ ->
        SilvaOptionumBO(
            /* @tt{{{
                @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
                @replace-value-by-expression
                    [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="CAMPUS_TEXTUS_OBLIGATORIUS" replaceByExpression="attribute.column.columnName" ]
            }}}@ */
            campusTextusObligatorius = resultSet.columnValue("CAMPUS_TEXTUS_OBLIGATORIUS"),
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            indexUnicus = resultSet.columnValue("INDEX_UNICUS"),
            campusTextusOptionalis = resultSet.columnValue("CAMPUS_TEXTUS_OPTIONALIS"),
            appellatio = resultSet.columnValue("APPELLATIO"),
            articulusInteriorSingularis = resultSet.columnValue("ARTICULUS_INTERIOR_SINGULARIS"),
            articulusInteriorIteratus = resultSet.columnValue("ARTICULUS_INTERIOR_ITERATUS"),
            articulusInteriorSingularisOptionalis = resultSet.columnValue("ARTICULUS_INTERIOR_SINGULARIS_OPTIONALIS"),
            articulusInteriorOptionalisIteratus = resultSet.columnValue("ARTICULUS_INTERIOR_OPTIONALIS_ITERATUS"),
            appellatioOptionalisIteratus = resultSet.columnValue("APPELLATIO_OPTIONALIS_ITERATUS"),
            campusDiei = resultSet.columnValue("CAMPUS_DIEI"),
            campusBivalens = resultSet.columnValue("CAMPUS_BIVALENS"),
            campusNumerorum = resultSet.columnValue("CAMPUS_NUMERORUM"),
            iteratioSimpliciumTextuum = resultSet.columnValue("ITERATIO_SIMPLICIUM_TEXTUUM"),
            relatioAdEntitatemOptionalisIteratus = resultSet.columnValue("RELATIO_AD_ENTITATEM_OPTIONALIS_ITERATUS"),
            relatioAdEntitatemOptionalis = resultSet.columnValue("RELATIO_AD_ENTITATEM_OPTIONALIS"),
            /* @tt{{{   @end-ignore-text  }}}@ */
        )
    }

    private val selectColumns: String
        get() {
            // because we need kotlin comments between the lines, we can not use kotlins multiline-comments
            val sb = StringBuilder()
            sb.appendLine("SELECT")
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="CAMPUS_TEXTUS_OBLIGATORIUS" replaceByExpression="column.columnName" ]
            }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OBLIGATORIUS,")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OPTIONALIS,")
            sb.appendLine("    APPELLATIO,")
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS,")
            sb.appendLine("    ARTICULUS_INTERIOR_ITERATUS,")
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS_OPTIONALIS,")
            sb.appendLine("    ARTICULUS_INTERIOR_OPTIONALIS_ITERATUS,")
            sb.appendLine("    APPELLATIO_OPTIONALIS_ITERATUS,")
            sb.appendLine("    CAMPUS_DIEI,")
            sb.appendLine("    CAMPUS_BIVALENS,")
            sb.appendLine("    CAMPUS_NUMERORUM,")
            sb.appendLine("    ITERATIO_SIMPLICIUM_TEXTUUM,")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS_ITERATUS,")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS,")
            /* @tt{{{   @end-ignore-text  }}}@ */
            // the primary key is rendered last, so that every looped line above can carry a comma
            sb.appendLine("    $PRIMARY_KEY_COLUMN_NAME")
            sb.appendLine("FROM $TABLE_NAME")
            return sb.toString()
    }
    private val insertStatement: String
        get() {
            // because we need kotlin comments between the lines, we can not use kotlin multiline-comments
            val sb = StringBuilder()
            sb.appendLine("INSERT INTO $TABLE_NAME (")
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="CAMPUS_TEXTUS_OBLIGATORIUS" replaceByExpression="column.columnName" ]
            }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OBLIGATORIUS,")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OPTIONALIS,")
            sb.appendLine("    APPELLATIO,")
            sb.appendLine("    CAMPUS_DIEI,")
            sb.appendLine("    CAMPUS_BIVALENS,")
            sb.appendLine("    CAMPUS_NUMERORUM,")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS,")
            /* @tt{{{   @end-ignore-text  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="ARTICULUS_INTERIOR_SINGULARIS" replaceByExpression="column.columnName" ]
            }}}@ */
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS,")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    ARTICULUS_INTERIOR_ITERATUS,")
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS_OPTIONALIS,")
            sb.appendLine("    ARTICULUS_INTERIOR_OPTIONALIS_ITERATUS,")
            sb.appendLine("    APPELLATIO_OPTIONALIS_ITERATUS,")
            sb.appendLine("    ITERATIO_SIMPLICIUM_TEXTUUM,")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS_ITERATUS,")
            /* @tt{{{   @end-ignore-text  }}}@ */
            sb.appendLine("    $PRIMARY_KEY_COLUMN_NAME")
            sb.appendLine(") VALUES (")
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="campusTextusObligatorius" replaceByExpression="column.attributeName.camelCase" ]
            }}}@ */
            sb.appendLine("    :campusTextusObligatorius,")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    :campusTextusOptionalis,")
            sb.appendLine("    :appellatio,")
            sb.appendLine("    :campusDiei,")
            sb.appendLine("    :campusBivalens,")
            sb.appendLine("    :campusNumerorum,")
            sb.appendLine("    :relatioAdEntitatemOptionalis,")
            /* @tt{{{   @end-ignore-text  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="articulusInteriorSingularis" replaceByExpression="column.attributeName.camelCase" ]
                    [ searchValue="jsonb" replaceByExpression="column.sqlTypeName" ]
            }}}@ */
            sb.appendLine("    CAST(:articulusInteriorSingularis AS jsonb),")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    CAST(:articulusInteriorIteratus AS jsonb),")
            sb.appendLine("    CAST(:articulusInteriorSingularisOptionalis AS jsonb),")
            sb.appendLine("    CAST(:articulusInteriorOptionalisIteratus AS jsonb),")
            sb.appendLine("    CAST(:appellatioOptionalisIteratus AS appellatio_comis[]),")
            sb.appendLine("    CAST(:iteratioSimpliciumTextuum AS text[]),")
            sb.appendLine("    CAST(:relatioAdEntitatemOptionalisIteratus AS uuid[]),")
            /* @tt{{{   @end-ignore-text  }}}@ */
            // DEFAULT applies whatever hands out the key of this table, a sequence or a random uuid
            sb.appendLine("    DEFAULT")
            sb.appendLine(")")
            sb.appendLine("RETURNING $PRIMARY_KEY_COLUMN_NAME")
            return sb.toString()
    }
    private val updateStatement: String
        get() {
            // because we need kotlin comments between the lines, we can not use kotlin multiline-comments
            val sb = StringBuilder()
            sb.appendLine("UPDATE $TABLE_NAME SET")
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filterNot { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="CAMPUS_TEXTUS_OBLIGATORIUS" replaceByExpression="column.columnName" ]
                    [ searchValue="campusTextusObligatorius" replaceByExpression="column.attributeName.camelCase" ]
            }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OBLIGATORIUS = :campusTextusObligatorius,")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    CAMPUS_TEXTUS_OPTIONALIS = :campusTextusOptionalis,")
            sb.appendLine("    APPELLATIO = :appellatio,")
            sb.appendLine("    CAMPUS_DIEI = :campusDiei,")
            sb.appendLine("    CAMPUS_BIVALENS = :campusBivalens,")
            sb.appendLine("    CAMPUS_NUMERORUM = :campusNumerorum,")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS = :relatioAdEntitatemOptionalis,")
            /* @tt{{{   @end-ignore-text  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.table.columnsWithoutPrimaryKey.filter { it.requiresParameterCast }" loopVariable="column" ]
                @replace-value-by-expression
                    [ searchValue="ARTICULUS_INTERIOR_SINGULARIS" replaceByExpression="column.columnName" ]
                    [ searchValue="articulusInteriorSingularis" replaceByExpression="column.attributeName.camelCase" ]
                    [ searchValue="jsonb" replaceByExpression="column.sqlTypeName" ]
            }}}@ */
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS = CAST(:articulusInteriorSingularis AS jsonb),")
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            sb.appendLine("    ARTICULUS_INTERIOR_ITERATUS = CAST(:articulusInteriorIteratus AS jsonb),")
            sb.appendLine("    ARTICULUS_INTERIOR_SINGULARIS_OPTIONALIS = CAST(:articulusInteriorSingularisOptionalis AS jsonb),")
            sb.appendLine("    ARTICULUS_INTERIOR_OPTIONALIS_ITERATUS = CAST(:articulusInteriorOptionalisIteratus AS jsonb),")
            sb.appendLine("    APPELLATIO_OPTIONALIS_ITERATUS = CAST(:appellatioOptionalisIteratus AS appellatio_comis[]),")
            sb.appendLine("    ITERATIO_SIMPLICIUM_TEXTUUM = CAST(:iteratioSimpliciumTextuum AS text[]),")
            sb.appendLine("    RELATIO_AD_ENTITATEM_OPTIONALIS_ITERATUS = CAST(:relatioAdEntitatemOptionalisIteratus AS uuid[]),")
            /* @tt{{{   @end-ignore-text  }}}@ */
            // assigning the key to itself is a no-op and keeps the last line free of a comma
            sb.appendLine("    $PRIMARY_KEY_COLUMN_NAME = :indexUnicus")
            sb.appendLine("WHERE $PRIMARY_KEY_COLUMN_NAME = :indexUnicus")
            return sb.toString()
    }
}
