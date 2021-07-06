export interface InvoiceLogsResponse{
     id?: number;
     logDate:Date;
     logNote:string;
     logStatus:string;
     logDataBefore: string;
     logDataAfter:string
}