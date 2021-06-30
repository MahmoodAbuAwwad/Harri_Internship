export class User {
    constructor(
      public firstName: string,
      public lastName: string,
      public email: string,
      public address: string,
      public role: string,
      public age: number, 
      public password: string,
      public id?: number) {
    }
  }