package hr.kmilos21.entity;

public class LinkMessage {
    private String message;
    private Boolean success;

    public LinkMessage() {}

    private LinkMessage(LinkMessageBuilder builder){
        this.message = builder.getMessage();
        this.success = builder.isSuccess();
    }

    public String getMessage() {
        return message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public static class LinkMessageBuilder{
        private String message;
        private Boolean success;

        public LinkMessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public LinkMessageBuilder success(Boolean success) {
            this.success = success;
            return this;
        }

        public LinkMessage build() {
            return new LinkMessage(this);
        }

        public String getMessage() {
            return message;
        }

        public Boolean isSuccess() {
            return success;
        }
    }
}
