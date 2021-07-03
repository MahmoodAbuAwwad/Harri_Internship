export const API_CONST = {
    BASE_URL: 'http://localhost:8888/',
    ACTIONS: {
      LOGIN: 'users/login',
      SIGNUP: 'users/signup',
      LOGGED_IN: 'users/logged-in'
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