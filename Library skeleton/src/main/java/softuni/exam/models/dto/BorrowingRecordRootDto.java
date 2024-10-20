package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "borrowing_records")
public class BorrowingRecordRootDto {

    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordSeedDto> borrowingRecordSeedDto;

    public List<BorrowingRecordSeedDto> getBorrowingRecordSeedDto() {
        return borrowingRecordSeedDto;
    }

    public void setBorrowingRecordSeedDto(List<BorrowingRecordSeedDto> borrowingRecordSeedDto) {
        this.borrowingRecordSeedDto = borrowingRecordSeedDto;
    }
}
