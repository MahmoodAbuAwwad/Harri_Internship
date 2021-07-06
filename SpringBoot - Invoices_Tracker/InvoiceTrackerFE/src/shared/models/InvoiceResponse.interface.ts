import { Item } from "./Item.model";
import { User } from "./user.model";

export interface InvoiceResponse{
     invoiceTotal: number;
     invoiceType:string;
     user:User;
     fileType:string;
     invoiceDate: Date;
     filePath:string
     id?: number;

}