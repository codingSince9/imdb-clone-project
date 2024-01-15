import { Injectable } from "@angular/core"
import { BehaviorSubject, Observable, ReplaySubject, Subject } from "rxjs";

@Injectable({providedIn: 'root'})
export class GlobalVariablesService {

    public loggedInFlag = true;
    public userId: string | undefined;
    private data = new BehaviorSubject("")
    currentData = this.data.asObservable();

    constructor() { }

    setData(data: any) {
        this.data.next(data);
    }

}
