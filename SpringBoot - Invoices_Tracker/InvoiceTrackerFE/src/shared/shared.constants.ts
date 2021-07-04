export const API_CONST = {
    BASE_URL: 'http://localhost:8888/',
    ACTIONS: {
      LOGIN: 'users/login',
      SIGNUP: 'users/signup',
      LOGGED_IN: 'users/logged-in',
      GET_USERS: 'users',
      DELETE_USER:'users/delete/',
      EDIT_USER: 'users/edit',
      GET_ITEMS:'items',
      DELETE_ITEM:'items/delete/',
      EDIT_ITEM:'items/edit',
      ADD_ITEM:'items'
    }
  };
  export const ROLES = {
    SUPERUSER: 0,
    AUDITOR: 1,
    USER: 2
  };

  export const INVOICE_TYPE = {
    TIMESHEET:"timesheet",
    COMMERCIAL:"commercial",
    MIXED:"mixed"
  }
  export const ATTACHMENT_TYPE = {
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