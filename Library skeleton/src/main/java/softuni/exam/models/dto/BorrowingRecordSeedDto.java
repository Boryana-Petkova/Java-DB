package softuni.exam.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordSeedDto {

    //•	borrow date - a date in the "yyyy-MM-dd" format.
    //•	return date - a date in the "yyyy-MM-dd" format.
    //•	remarks - can be used to store any relevant information and might be
    // helpful for tracking and managing the borrowing records. Accepts char sequence (between 3 to 100 inclusive). Can be nullable.


    //<borrowing_records>
    //    <borrowing_record>
    //        <borrow_date>2020-01-13</borrow_date>
    //        <return_date>2022-09-11</return_date>
    //        <book>
    //            <title>The Lord of the Rings</title>
    //        </book>
    //        <member>
    //            <id>27</id>
    //        </member>
    //        <remarks>Handle with care, fragile book.</remarks>
    //    </borrowing_record>

    @XmlElement(name = "borrow_date")
    @NotNull
    private String borrowDate;

    @XmlElement(name = "return_date")
    @NotNull
    private String returnDate;

    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

    @XmlElement(name = "book")
    @NotNull
    private BookDto book;


    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberDto member;



    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LibraryMemberDto getMember() {
        return member;
    }

    public void setMember(LibraryMemberDto member) {
        this.member = member;
    }
}
