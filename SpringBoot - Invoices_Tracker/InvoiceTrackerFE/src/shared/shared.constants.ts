export const API_CONST = {
    BASE_URL: 'http://localhost:8888/',
    ACTIONS: {
      LOGIN: 'login',
      SIGNUP: 'signup',
      LOGGED_IN: 'logged-in',
      GET_USERS: 'users',
      DELETE_USER:'users/delete/',
      EDIT_USER: 'users',
      GET_ITEMS:'items',
      DELETE_ITEM:'items/',
      EDIT_ITEM:'items',
      ADD_ITEM:'items',
      ADD_INVOICE:'invoices',
      GET_INVOICES:'invoices',
      DELETE_INVOICE:'invoices/',
      PREVIEW_GET_INVOICE:'invoices/preview/',
      PREVIEW_GET_INVOICE_LOGS:'invoices/preview/logs/',
      GET_INVOICE_ITEMS:'invoices/items/',
      EDIT_INVOICE:'invoices/'

    }
  };
  export const ROLES = {
    SUPERUSER: 0,
    AUDITOR: 1,
    USER: 2
  };

  export const INVOICE_TYPE = {
    PAID:"paid",
    NOT_PAID:"not_paid",
    HALF_PAID:"half_paid"
  }

  export const INVOICE_TYPE_E = {
    PAID:"PAID",
    NOT_PAID:"NOT_PAID",
    HALF_PAID:"HALF_PAID"
  }
  export const ATTACHMENT_TYPE = {
    NONE:"",
    IMAGE:"image",
    FILE:"file",
  }

  export const STORAGE = {
    TOKEN: 'token',
    USER: 'user'
  };

  export const ROUTES = {
    LOGIN: 'login',
    DASHBOARD: 'dashboard',
  };