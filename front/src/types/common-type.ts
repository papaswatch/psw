
export type KeyValue<K, V> = {
  key: K;
  value: V;
}

export type Response <T> = {
  code: number;
  status: string;
  data?: T;
}

export type PageData <T> = {
  total: number;
  list?: T;
}