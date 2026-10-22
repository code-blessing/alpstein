import {parseViewToken, ViewDescriptor, viewToken} from '@app/view-tabs/view-descriptor';

describe('viewToken', () => {

    it('should round trip every view kind', () => {
        const views: ReadonlyArray<ViewDescriptor> = [
            {entityName: 'film', viewKind: 'board'},
            {entityName: 'film-actor', viewKind: 'create'},
            {entityName: 'country', viewKind: 'edit', itemId: '5'},
            {entityName: 'opus-magnum', viewKind: 'edit', itemId: '3f7c1e2a-0000-4000-8000-000000000001'},
        ];

        views.forEach(view => expect(parseViewToken(viewToken(view))).toEqual(view));
    });

    it('should keep the dashes of the entity name out of the view kind', () => {
        expect(viewToken({entityName: 'film-actor', viewKind: 'edit', itemId: '7'})).toBe('film-actor.edit.7');
        expect(parseViewToken('film-actor.edit.7')).toEqual({entityName: 'film-actor', viewKind: 'edit', itemId: '7'});
    });

    it('should not parse a malformed token', () => {
        const malformedTokens = [
            '',
            'film',
            'film.',
            'film.edit',
            'film.board.5',
            'film.unknown',
            '.board',
        ];

        malformedTokens.forEach(token => expect(parseViewToken(token)).withContext(token).toBeNull());
    });
});
