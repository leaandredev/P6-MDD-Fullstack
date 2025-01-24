export interface User {
  id?: number;
  email: string;
  userName: string;
  password?: string;
  subscriptions?: number[];
  createdAt?: Date;
  updatedAt?: Date;
}
