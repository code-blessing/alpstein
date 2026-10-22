import {Component} from '@angular/core';
import {TranslocoPipe} from '@jsverse/transloco';

@Component({
    selector: 'app-start-page',
    templateUrl: './start-page.component.html',
    styleUrls: ['./start-page.component.scss'],
    imports: [
        TranslocoPipe,
    ],
})
export class StartPageComponent {
}
