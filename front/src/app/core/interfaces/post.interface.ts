export interface Post {
  id?: number;
  title: string;
  content: string;
  topicId: number;
  userId: number;
  createdAt?: Date;
  updatedAt?: Date;
}
