package service.encryptor;

public interface Encryptor {

     String hash(char[] password);
     boolean checkPassword(char[] password, String token);

}
