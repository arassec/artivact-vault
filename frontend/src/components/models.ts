export interface Todo {
  id: number;
  content: string;
}

export interface Meta {
  totalCount: number;
}

export interface ArtivactCardData {
  artivactId: string;
  title: string;
  imageUrl: string;
}

export interface ArtivactDetails {
  id: string;
  version: number;
  name: string;
  imageUrls: string[];
  modelUrls: string[];
}
