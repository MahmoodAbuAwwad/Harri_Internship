import { Item } from "./Item.model";
import { User } from "./user.model";

export class Invoice {
    constructor(
        public total: number,
        public invoiceType:string,
        public user:User,
        public file_type:string,
        public file:File,
        public items:Item[],
        public id?: number,
        public note?:string) {
    }
}