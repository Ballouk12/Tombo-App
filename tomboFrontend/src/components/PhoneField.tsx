import React from "react";
import PhoneInput from "react-phone-number-input";
interface PhoneFieldProps {
  phone: string;
  setPhone: (value: string) => void;
}

 const PhoneField: React.FC<PhoneFieldProps> = ({ phone, setPhone }) => {
  return (
    <div className="space-y-1">
      <div className="w-full rounded-md border border-input bg-background text-foreground px-3 py-2 focus-within:ring-1 focus-within:ring-ring  bg-white  text-black">
        <PhoneInput
          id="phone"
          placeholder="Entrez votre numéro"
          value={phone}
          onChange={(value) => setPhone(value || "")}
          defaultCountry="MA"
          international
          className="w-full bg-transparent  border-none  focus:outline-none  placeholder:text-muted-foreground text-black"
        />
      </div>
    </div>
  );
};
export default PhoneField;