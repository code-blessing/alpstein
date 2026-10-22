

import {inject} from '@angular/core';
import {CanActivateFn, Router, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {MembrumRelatumService} from '@app/service/membrum-relatum.service';

/**
 * Resolves the first available {@link MembrumRelatumWTO} and redirects to its edit route,
 * i.e. {@code /entitas-relata-routable-edit/<clavisPrimaria>}. Used by the side navigation so a
 * link can open "the first entry" without knowing its id up front. Falls back to the board
 * when there is no entry to edit.
 */
export const entitasRelataFirstEntryEditGuard: CanActivateFn = (): Observable<UrlTree> => {
    const router = inject(Router);
    return inject(MembrumRelatumService).getMembrumRelatumList().pipe(
        map(membrumRelatumList => {
            const first = membrumRelatumList.at(0);
            return first
                ? router.createUrlTree(['/entitas-relata-routable-edit', first.clavisPrimaria])
                : router.createUrlTree(['/entitas-relata-board']);
        })
    );
};
