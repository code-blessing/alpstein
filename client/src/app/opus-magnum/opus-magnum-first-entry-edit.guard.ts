/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityFirstEntryGuardRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="OpusMagnum" replaceByExpression="model.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entityName.camelCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entityName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.entityRootItem.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.entityRootItem.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.entityRootItem.itemName.kebabCase" ]
        [ searchValue="SILVA_OPTIONUM" replaceByExpression="model.entityRootItem.itemName.screamingSnakeCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.idAttribute.attributeName.camelCase" ]


    @modify-provided-filepath-by-replacements



}}}@ */


import {inject} from '@angular/core';
import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {SilvaOptionumService} from '@app/service/silva-optionum.service';

/**
 * Resolves the first available {@link SilvaOptionumWTO} and redirects to its edit route,
 * i.e. {@code /opus-magnum-routable-edit/<indexUnicus>}. Used by the side navigation so a
 * link can open "the first entry" without knowing its id up front. Falls back to the board
 * when there is no entry to edit.
 */
export const opusMagnumFirstEntryEditGuard: CanActivateFn = (): Observable<UrlTree> => {
    const router = inject(Router);
    return inject(SilvaOptionumService).getSilvaOptionumList().pipe(
        map(silvaOptionumList => {
            const first = silvaOptionumList.at(0);
            return first
                ? router.createUrlTree(['/opus-magnum-routable-edit', first.indexUnicus])
                : router.createUrlTree(['/opus-magnum-board']);
        })
    );
};
