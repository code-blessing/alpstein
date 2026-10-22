/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemServiceRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="SILVA_OPTIONUM" replaceByExpression="model.itemName.screamingSnakeCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]


    @modify-provided-filepath-by-replacements

    

}}}@ */

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";
import {SilvaOptionumSearchCriteriaWTO} from "@app/wto/silva-optionum-search-criteria.wto";
import {SilvaOptionumSearchResultWTO} from "@app/wto/silva-optionum-search-result.wto";
import {SilvaOptionumByIdsCriteriaWTO} from "@app/wto/silva-optionum-by-ids-criteria.wto";
import {SilvaOptionumByIdsResultWTO} from "@app/wto/silva-optionum-by-ids-result.wto";
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */


@Injectable({providedIn: 'root'})
export class SilvaOptionumService {
    private readonly baseUrl = 'http://localhost:8081/api/silva-optionum';

    constructor(private readonly http: HttpClient) {}

    getSilvaOptionumList(): Observable<SilvaOptionumWTO[]> {
        return this.http.get<SilvaOptionumWTO[]>(this.baseUrl);
    }

    searchSilvaOptionumList(searchCriteria: SilvaOptionumSearchCriteriaWTO): Observable<SilvaOptionumSearchResultWTO> {
        return this.http.post<SilvaOptionumSearchResultWTO>(`${this.baseUrl}/search`, searchCriteria);
    }

    /**
     * Resolves a whole set of references to this item in one call, so that a list of stored
     * identifiers can be shown by the display attributes instead of the bare primary keys.
     */
    getSilvaOptionumListByIds(criteria: SilvaOptionumByIdsCriteriaWTO): Observable<SilvaOptionumByIdsResultWTO> {
        return this.http.post<SilvaOptionumByIdsResultWTO>(`${this.baseUrl}/by-ids`, criteria);
    }

    getSilvaOptionumById(indexUnicus: UUID): Observable<SilvaOptionumWTO | null> {
        return this.http.get<SilvaOptionumWTO | null>(`${this.baseUrl}/${indexUnicus}`);
    }

    deleteSilvaOptionum(indexUnicus: UUID): Observable<void> {
        return this.http.delete<void>(`${this.baseUrl}/${indexUnicus}`);
    }

    updateSilvaOptionum(silvaOptionum: SilvaOptionumWTO): Observable<SilvaOptionumWTO> {
        return this.http.put<SilvaOptionumWTO>(`${this.baseUrl}/${silvaOptionum.indexUnicus}`, silvaOptionum);
    }

    createSilvaOptionum(silvaOptionum: SilvaOptionumWTO): Observable<SilvaOptionumWTO> {
        return this.http.post<SilvaOptionumWTO>(this.baseUrl, silvaOptionum);
    }
}
