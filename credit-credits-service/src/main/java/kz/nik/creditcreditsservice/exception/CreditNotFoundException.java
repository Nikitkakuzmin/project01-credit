package kz.nik.creditcreditsservice.exception;

public class CreditNotFoundException extends RuntimeException{
    private Long id;
    private String name;


    public CreditNotFoundException(Long id){
        this.id = id;
    }
    public CreditNotFoundException(String name){
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Credit with name " + name + " not found ";
    }
}

