export class User {
    constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public address: string,
      public role: number,
      public age: number, 
      public password?: string,
      public id?: number) {
    }
  }