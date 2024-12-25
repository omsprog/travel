export interface SingInRequest {
  email: string;
  password: string;
}

export interface SignInResponse {
  email: string;
  jwtToken: string;
}
