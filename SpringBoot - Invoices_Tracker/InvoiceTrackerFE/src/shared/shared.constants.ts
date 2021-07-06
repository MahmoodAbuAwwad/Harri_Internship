export const API_CONST = {
    BASE_URL: 'http://localhost:8888/',
    ACTIONS: {
      LOGIN: 'login',
      SIGNUP: 'signup',
      LOGGED_IN: 'logged-in',
      GET_USERS: 'users',
      DELETE_USER:'users/delete/',
      EDIT_USER: 'users/edit',
      GET_ITEMS:'items',
      DELETE_ITEM:'items/delete/',
      EDIT_ITEM:'items/edit',
      ADD_ITEM:'items',
      ADD_INVOICE:'invoices',
      GET_INVOICES:'invoices',
      DELETE_INVOICE:'invoices/delete/',
      PREVIEW_GET_INVOICE:'invoices/preview/',
      PREVIEW_GET_INVOICE_LOGS:'invoices/preview/logs/'

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