export interface User {
  id?: number;
  email: string;
  userName: string;
  subscriptions?: number[];
  createdAt?: Date;
  updatedAt?: Date;
}
