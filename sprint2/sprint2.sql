create database sprint2;
use sprint2;

create table if not exists `account` (
	account_id bigint primary key auto_increment,
    username varchar(255) unique not null,
    `password` varchar(255)
);

create table if not exists `role` (
	role_id bigint primary key auto_increment,
    role_name varchar(255)
);

create table if not exists account_role (
	account_role_id bigint primary key auto_increment,				
	account_id bigint not null,				
	role_id bigint not null,				
	foreign key (account_id) references `account`(account_id) on update cascade,				
	foreign key (role_id) references `role`(role_id) on update cascade	  			
);

create table customer(
  customer_id bigint primary key auto_increment,
  customer_name varchar(255),
  customer_phone varchar(255) unique, 
  customer_email varchar(255) unique,
  customer_image varchar(255),
  customer_address varchar(255),
  customer_account_id bigint unique,
  foreign key (customer_account_id) references `account` (account_id) on update cascade
);

create table if not exists category(
  category_id bigint primary key auto_increment,
  category_name varchar(255)
);

create table if not exists author(
  author_id bigint primary key auto_increment,
  author_name varchar(255),
  author_avatar varchar(255)
);

create table if not exists promotion(
	promotion_id bigint primary key auto_increment,		
    promotion_percent double
);

create table if not exists book(
  book_id bigint primary key auto_increment,
  book_code varchar(255),
  book_name varchar(255),
  book_image varchar(255),
  book_content text,
  book_price double,
  book_translator varchar(255),
  book_total_page int,
  book_size varchar(255),
  book_publish_date date,
  book_quantity int not null,					
  book_flag bit(1) default 0,
  book_publisher varchar(255),
  book_category_id bigint,
  book_author_id bigint,
  book_promotion_id bigint default 1,
  
  foreign key(book_promotion_id) references promotion(promotion_id) on update cascade,
  foreign key(book_category_id) references category(category_id) on update cascade,
  foreign key(book_author_id) references author(author_id) on update cascade
);

create table if not exists cart (					
	cart_id bigint primary key auto_increment,	
    cart_code varchar(255),
	cart_quantity int not null,					
	cart_total_money double not null,	
	cart_purchase_date date,
    cart_status bit(1) default 0,
	cart_account_id bigint,												
	foreign key (cart_account_id) references `account`(account_id)
);

create table if not exists cart_book (					
	cart_book_id bigint primary key auto_increment,		
    book_id bigint not null,
	cart_id bigint not null,					
	foreign key(cart_id) references cart(cart_id) on delete cascade,					
	foreign key(book_id) references book(book_id) on update cascade					
);

	INSERT INTO `sprint2`.`account` (`username`, `password`) VALUES ('admin', '$2a$10$lqnvHKHPyKkZWMMtBB.jy.hMuPaSNH/Nlfuj6XaHCRLB7PK.EU60K');
	INSERT INTO `sprint2`.`account` (`username`, `password`) VALUES ('customer', '$2a$10$PkK1zRmB8v/Ei4cD.nuTB.8FzdTLAXVTPcgYkLACrEy5jZsvehXn6');

	INSERT INTO `sprint2`.`role` (`role_name`) VALUES ('ROLE_ADMIN');
	INSERT INTO `sprint2`.`role` (`role_name`) VALUES ('ROLE_CUSTOMER');

	INSERT INTO `sprint2`.`account_role` (`account_id`, `role_id`) VALUES ('1', '1');
	INSERT INTO `sprint2`.`account_role` (`account_id`, `role_id`) VALUES ('2', '2');
    
    INSERT INTO `sprint2`.`customer` (`customer_name`, `customer_phone`, `customer_email`, `customer_image`, `customer_address`, `customer_account_id`) VALUES ('Nguyễn Văn A', '0905111111', 'nguyenvana@gmail.com', 'https://demoda.vn/wp-content/uploads/2022/02/avatar-anime-cute-562x600.jpg', 'Đà Nẵng', '1');
	INSERT INTO `sprint2`.`customer` (`customer_name`, `customer_phone`, `customer_email`, `customer_image`, `customer_address`, `customer_account_id`) VALUES ('Nguyễn Văn B', '0905222222', 'nguyenvanb@gmail.com', 'https://demoda.vn/wp-content/uploads/2022/02/avatar-anime-cuc-de-thuong-600x600.jpg', 'Hà Nội', '2');

	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Lãng mạn');
	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Phiêu lưu hành động');
	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Trẻ em');
	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Khoa học viễn tưởng');
	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Rùng rợn');
	INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Tiểu thuyết lịch sử');
    INSERT INTO `sprint2`.`category` (`category_name`) VALUES ('Văn học Việt Nam');

    INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Jane Austen', 'https://www.studytienganh.vn/upload/2022/05/112275.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Laura Scott', 'https://www.studytienganh.vn/upload/2022/05/112274.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Sir Malory Thomas', 'https://www.studytienganh.vn/upload/2022/05/112273.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Jules Verne', 'https://www.studytienganh.vn/upload/2022/05/112272.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Alexandre Dumas', 'https://www.studytienganh.vn/upload/2022/05/112280.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Mark Twain', 'https://www.studytienganh.vn/upload/2022/05/112279.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Frances Hodgson Burnett', 'https://www.studytienganh.vn/upload/2022/05/112278.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Lewis Carroll', 'https://www.studytienganh.vn/upload/2022/05/112277.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Angela Brazil', 'https://www.studytienganh.vn/upload/2022/05/112276.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('H. G. Wells', 'https://www.studytienganh.vn/upload/2022/05/112284.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('H. P. Lovecraft', 'https://www.studytienganh.vn/upload/2022/05/112282.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Bram Stoker', 'https://www.studytienganh.vn/upload/2022/05/112281.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('William Shakespeare', 'https://truongtin.top/wp-content/uploads/2021/09/23/08/avatar-chibi-avatar-cute-anh-dai-dien-chibi-dep-1.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Oscar Wilde', 'https://truongtin.top/wp-content/uploads/2021/09/23/08/avatar-chibi-avatar-cute-anh-dai-dien-chibi-dep-2.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('John Dryden', 'https://truongtin.top/wp-content/uploads/2021/09/23/08/avatar-chibi-avatar-cute-anh-dai-dien-chibi-dep-3.jpg');
    INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Ngọc (Bi) Nguyễn', 'https://www.studytienganh.vn/upload/2022/05/112275.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Nguyễn Tuân', 'https://www.studytienganh.vn/upload/2022/05/112274.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Trần Vàng Sao', 'https://www.studytienganh.vn/upload/2022/05/112273.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Blog của May', 'https://www.studytienganh.vn/upload/2022/05/112272.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Zihua Nguyễn', 'https://www.studytienganh.vn/upload/2022/05/112280.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Nguyễn Hoàng Vũ', 'https://truongtin.top/wp-content/uploads/2021/09/23/08/avatar-chibi-avatar-cute-anh-dai-dien-chibi-dep-1.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Hoàng Minh Tường', 'https://www.studytienganh.vn/upload/2022/05/112282.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Phan Việt', 'https://www.studytienganh.vn/upload/2022/05/112278.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('Phan Vũ', 'https://www.studytienganh.vn/upload/2022/05/112279.jpg');
	INSERT INTO `sprint2`.`author` (`author_name`, `author_avatar`) VALUES ('BS. Vân Hương', 'https://www.studytienganh.vn/upload/2022/05/112273.jpg');
    
    
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('0');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('5');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('10');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('15');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('20');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('25');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('50');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('75');
	INSERT INTO `sprint2`.`promotion` (`promotion_percent`) VALUES ('90');
    
    INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-001', 'Pride and Prejudice', 'https://m.media-amazon.com/images/I/51tiK-eB3JL._SY346_.jpg', 'Bộ phim hài hay nhất về cách cư xử của Austen miêu tả cuộc sống trong xã hội nông thôn dịu dàng vào đầu những năm 1800, và kể về những hiểu lầm ban đầu (và sự giác ngộ lẫn nhau) giữa Elizabeth Bennet hoạt bát và nhanh trí và ông Darcy kiêu kỳ.', '1000', 'Nguyen Van A', '100', '30x40cm', '2020-01-01', '5', 'Le Van A', '1', '1', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-002', 'Emma', 'https://fb2bookfree.com/uploads/posts/2020-04/1587923554_61wz2omujdl.jpg', 'Nhân vật chính, Emma Woodhouse, được miêu tả trong đoạn mở đầu là \'\' đẹp trai, thông minh và giàu có \'\' nhưng cũng khá hư hỏng. Là kết quả của cuộc hôn nhân gần đây với người quản lý cũ của mình, Emma tự hào về khả năng mai mối của mình và tiếp tục nhận nuôi một đứa trẻ mồ côi ngoài giá thú, Harriet Smith, người mà cô hy vọng sẽ kết hôn với cha sở, ông Elton. Cô tự tin đến mức thuyết phục Harriet từ chối lời cầu hôn từ một người nông dân trẻ, người là đối tác phù hợp hơn với cô gái.', '2000', 'Nguyen Van B', '150', '30x40cm', '2020-02-02', '5', 'Le Van B', '1', '1', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-003', 'Healing Her Heart', 'https://m.media-amazon.com/images/I/5121EiDJnxL._SY346_.jpg', 'Tiến sĩ Gabe Allen có một quy tắc về việc hẹn hò với đồng nghiệp nhưng khi gặp y tá Larissa Brockman của ER, anh ta đã bị cám dỗ để phá bỏ lời thề của mình. Niềm tin của Larissa kéo anh ta trở lại nhà thờ mà anh ta đã bỏ lại, nhưng khi cuộc sống của họ đang đi trên đường cùng, Gabe phát hiện ra rằng Larissa là người cần phải học về ý nghĩa thực sự của sự tha thứ. Và chỉ có Gabe mới có thể giúp chữa lành trái tim cô ấy.', '3000', 'Nguyen Van C', '200', '30x40cm', '2020-03-03', '5', 'Le Van C', '1', '2', '2');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-004', 'Le Morte D Arthur, vol 1', 'https://kbimages1-a.akamaihd.net/ae43adee-3168-4890-914b-552eb33bdc92/353/569/90/False/le-morte-d-arthur-36.jpg', 'Ngài Launcelot đã đón nhận con gái của Vua Bagdemagus như thế nào, và ông đã khiếu nại với cha cô như thế nào.', '4000', 'Nguyen Van D', '250', '30x40cm', '2020-04-04', '5', 'Le Van D', '1', '3', '2');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-005', '20,000 Leagues Under the Sea', 'https://m.media-amazon.com/images/I/51NPDABN5LL._SY291_BO1,204,203,200_QL40_FMwebp_.jpg', 'Được cử đến để điều tra những cuộc chạm trán bí ẩn đang làm gián đoạn việc vận chuyển quốc tế, Giáo sư Aronnax, người hầu của ông ta là Conseil, và người thợ làm nghề bất mãn Ned Land bị bắt khi tàu khu trục của họ bị đánh chìm trong cuộc chạm trán với \"quái vật\". Chiếc tàu ngầm Nautilus và Thuyền trưởng Nemo lập dị của nó mang đến cho giáo sư và những người bạn đồng hành của ông niềm say mê và nguy hiểm vô tận khi họ bị cuốn theo chuyến du hành dưới đáy biển kéo dài hàng năm trời.', '5000', 'Nguyen Van E', '300', '30x40cm', '2020-05-05', '5', 'Le Van E', '2', '4', '3');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-006', 'The Count of Monte Cristo', 'https://english-e-reader.net/covers/The_Count_of_Monte_Cristo-Alexandre_Dumas.jpg', 'Một cuốn tiểu thuyết phiêu lưu kinh điển, thường được coi là tác phẩm hay nhất của Dumas, và thường xuyên được đưa vào danh sách những tiểu thuyết hay nhất mọi thời đại. Hoàn thành vào năm 1844, và được phát hành dưới dạng một loạt 18 phần trong hai năm tiếp theo, Dumas đã hợp tác với các tác giả khác trong suốt thời gian đó. Câu chuyện xảy ra ở Pháp, Ý và Địa Trung Hải từ cuối thời kỳ trị vì của Napoléon I đến triều đại của Louis-Philippe.', '6000', 'Nguyen Van F', '350', '30x40cm', '2020-06-06', '5', 'Le Van F', '2', '5', '3');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-007', 'Adventures of Huckleberry Finn', 'https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781481403757/the-adventures-of-huckleberry-finn-9781481403757_lg.jpg', 'Hành trình trôi dạt của Huck và người bạn Jim, một nô lệ bỏ trốn, xuống sông Mississippi trên chiếc bè của họ có thể là một trong những hình ảnh lâu dài nhất về sự trốn chạy và tự do trong văn học Mỹ. Mặc dù xã hội mà nó châm biếm đã là lịch sử vào thời điểm xuất bản, cuốn sách đã gây khá nhiều tranh cãi, và vẫn như vậy cho đến ngày nay.', '7000', 'Nguyen Van G', '400', '30x40cm', '2020-07-07', '5', 'Le Van G', '2', '6', '4');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-008', 'A Little Princess', 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1328861169l/38647.jpg', 'Sara Crewe, một học sinh tại trường Miss Minchin\'s London, bị bỏ mặc trong cảnh nghèo khó khi cha cô qua đời, nhưng sau đó được một nhà hảo tâm bí ẩn cứu thoát.', '8000', 'Nguyen Van H', '450', '30x40cm', '2020-08-08', '5', 'Le Van H', '3', '7', '4');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-009', 'Alice in Wonderland', 'https://upload.wikimedia.org/wikipedia/commons/c/ca/Unifon_-_Alice%27s_Adventures_in_Wonderland_-_book_cover.jpg', 'Câu chuyện về một cô gái tên Alice rơi xuống hố thỏ và lạc vào một thế giới tưởng tượng với những sinh vật kỳ dị và nhân hình. Câu chuyện chứa đầy những ám chỉ về những người bạn của Dodgson. Câu chuyện chơi với logic theo những cách đã làm cho câu chuyện trở nên phổ biến lâu dài với người lớn cũng như trẻ em. Nó được coi là một trong những ví dụ tiêu biểu nhất của thể loại văn học vô nghĩa, và trình tự và cấu trúc tường thuật của nó đã có ảnh hưởng rất lớn, đặc biệt là trong thể loại kỳ ảo.', '9000', 'Nguyen Van I', '500', '30x40cm', '2020-09-09', '5', 'Le Van I', '3', '8', '5');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-010', 'A Pair of Schoolgirls', 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1275422933l/8161021.jpg', 'đặc biệt là những người thuộc Phần ba Trung và Hạ, dường như thích tầm quan trọng của việc sở hữu một thứ gì đó mà họ có quyền giữ lại.', '10000', 'Nguyen Van J', '450', '30x40cm', '2020-10-10', '5', 'Le Van J', '3', '9', '5');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-011', 'The Time Machine', 'https://kbimages1-a.akamaihd.net/acc6e75e-e075-441b-9fcf-773e458364c1/353/569/90/False/the-time-machine-314.jpg', 'Một tưởng tượng tuyệt vời vượt ra ngoài suy nghĩ thông thường ...', '11000', 'Nguyen Van K', '400', '30x40cm', '2020-11-11', '5', 'Le Van K', '4', '10', '6');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-012', 'The Invisible Man', 'https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781683832027/h-g-wells-the-invisible-man-9781683832027_lg.jpg', 'Người vô hình có tựa đề là \'\' Griffin \'\', một nhà khoa học đưa ra giả thuyết rằng nếu chỉ số khúc xạ của một người được thay đổi thành chính xác của không khí và cơ thể anh ta không hấp thụ hoặc phản xạ ánh sáng, thì anh ta sẽ không thể nhìn thấy được. Anh ta tự mình thực hiện thành công thủ thuật này, nhưng bắt đầu trở nên bất ổn về tinh thần do ...', '12000', 'Nguyen Van L', '350', '30x40cm', '2020-12-12', '5', 'Le Van L', '4', '10', '6');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-013', 'The War of the Worlds', 'https://www.gutenberg.org/files/36/36-h/images/cover.jpg', 'The War of the Worlds mô tả cuộc xâm lược hư cấu vào Trái đất năm 1895 của người ngoài hành tinh từ Sao Hỏa, những người sử dụng Tia nhiệt giống như tia laser, vũ khí hóa học và \'\' cỗ máy chiến đấu \'\' ba chân cơ học có khả năng được coi là tiền thân của xe tăng. Sau khi đánh bại cuộc kháng chiến, người Sao Hỏa tàn phá phần lớn miền đông nước Anh, bao gồm cả London ...', '13000', 'Nguyen Van M', '300', '30x40cm', '2019-01-01', '5', 'Le Van M', '4', '10', '7');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-014', 'The Mysterious Island', 'http://www.loyalbooks.com/image/detail/Mysterious-Island.jpg', 'Cuốn sách kể về cuộc phiêu lưu của 5 tù binh Mỹ trên một hòn đảo chưa được khám phá ở Nam Thái Bình Dương. Bắt đầu từ Nội chiến Hoa Kỳ, khi nạn đói và cái chết hoành hành thành phố Richmond, Virginia, năm tù binh tù binh miền bắc quyết định trốn thoát theo một cách khá bất thường - bằng cách cướp một quả khinh khí cầu! Đây chỉ là khởi đầu cho cuộc phiêu lưu của họ ...', '14000', 'Nguyen Van N', '250', '30x40cm', '2019-02-02', '5', 'Le Van N', '4', '4', '7');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-015', 'The Call of Cthulhu', 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1635225102l/58085210.jpg', 'Ba câu chuyện độc lập được liên kết với nhau bởi thiết bị của một người kể chuyện khám phá các ghi chú của một người thân đã khuất để lại. Kết hợp toàn bộ sự thật và tầm quan trọng đáng lo ngại của thông tin mà anh ta sở hữu, dòng cuối cùng của người kể chuyện là \'\' Điều nhân từ nhất trên thế giới, tôi nghĩ, là trí óc con người không có khả năng tương quan với tất cả nội dung của nó.', '15000', 'Nguyen Van O', '100', '30x40cm', '2019-03-03', '5', 'Le Van O', '5', '11', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-016', 'At the Mountains of Madness', 'https://upload.wikimedia.org/wikipedia/en/thumb/f/fc/Lovecraft%2C_Mountains_of_Madness.jpg/300px-Lovecraft%2C_Mountains_of_Madness.jpg', 'Trong một chuyến thám hiểm đến Nam Cực, Giáo sư William Dyer và các đồng nghiệp của ông đã phát hiện ra tàn tích của các dạng sống nửa thực vật, nửa động vật cổ đại. Việc xác định niên đại cực kỳ sớm trong các tầng địa chất là điều đáng ngạc nhiên vì các đặc điểm tiến hóa cao được tìm thấy trong các dạng sống chưa từng được biết đến trước đây này. Thông qua một loạt tiết lộ đen tối, các tình tiết bạo lực và hiểu lầm, cả nhóm biết được lịch sử và di sản bí mật của Trái đất.', '16000', 'Nguyen Van P', '150', '30x40cm', '2019-04-04', '5', 'Le Van P', '5', '11', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-017', 'Dracula', 'https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781476788104/dracula-9781476788104_lg.jpg', 'Câu chuyện về ma cà rồng nổi tiếng nhất thế giới bắt đầu bằng việc theo chân một thanh niên người Anh ngây thơ khi đến thăm Transylvania để gặp một khách hàng, đó là Bá tước Dracula bí ẩn. Sau khi tiết lộ bản chất thật của mình, Dracula lên một con tàu đến Anh, nơi những thảm họa rùng rợn và rùng rợn bắt đầu ập đến với người dân London ...', '17000', 'Nguyen Van Q', '200', '30x40cm', '2019-05-05', '5', 'Le Van Q', '5', '12', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-018', 'The Complete Works of William Shakespeare', 'https://m.media-amazon.com/images/I/61Vt5l7M84L._SY291_BO1,204,203,200_QL40_FMwebp_.jpg', 'Mọi tác phẩm đã biết của Bard, trong một tập lớn.', '18000', 'Nguyen Van R', '250', '30x40cm', '2019-06-06', '5', 'Le Van R', '6', '13', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-019', 'Macbeth', 'https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781451694727/macbeth-9781451694727_lg.jpg', 'Bản tường trình về một vụ tự sát và hậu quả của nó.', '19000', 'Nguyen Van S', '300', '30x40cm', '2019-07-07', '5', 'Le Van S', '6', '13', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-020', 'The Importance of Being Earnest', 'https://g.christianbook.com/dg/product/cbd/f400/264783.jpg', 'Một trò hề, một trong những tác phẩm hay nhất từng được viết, được xây dựng thông minh và gây cười thú vị. Chỉ có một nỗ lực nhỏ nhất trong việc phác họa nhân vật, trong khi hầu hết các nhân vật tốt nhất là tranh biếm họa; kỹ năng của Wilde được phát huy chủ yếu dựa trên các tình huống và lời thoại. Điều xảy ra là trò hề này chứa nhiều câu thoại, lối chơi chữ, biểu tượng và những câu trả lời khéo léo hơn bất kỳ trò nào khác của thời hiện đại, nhưng xét cho cùng thì đây cũng chỉ là phụ kiện. Một trò hề có thể được viết mà không có những bổ sung này - nó cũng có thể là một vở kịch câm thuần túy. Wilde đã ném chúng vào cho đầy đủ.', '20000', 'Nguyen Van T', '350', '30x40cm', '2019-08-08', '5', 'Le Van T', '6', '14', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-021', 'All For Love', 'https://m.media-amazon.com/images/I/51uvIiGNXuL._SX218_BO1,204,203,200_QL40_FMwebp_.jpg', 'Một bi kịch.Nam tước thứ hai Osborne của Kiveton, ở Yorkshire; Thủ quỹ tối cao của Vương quốc Anh, một trong những Hội đồng Cơ mật Danh giá nhất của Bệ hạ, và là Hiệp sĩ của Huân chương Cao quý nhất của Garter.', '21000', 'Nguyen Van U', '400', '30x40cm', '2019-09-09', '5', 'Le Van U', '6', '15', '2');
    INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-022', 'Đám trẻ nhiễu văn hoá', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/6/21/25TXXGOL.jpg', 'Sinh ra ở Moscow bởi bố mẹ người Việt Nam nhưng lại tin rằng mình có phần nào đó là người Xô viết, chuyển về Hà Nội khi lên 3 và vào học trường Quốc tế Pháp dưới sự ủng hộ của ông bà, 15 tuổi rời Việt Nam sang Mỹ để học tại một trường nội trú ở tiểu bang Connecticut, câu hỏi mà Ngọc Nguyễn, cũng như những đứa trẻ nhiễu văn hóa khác, cảm thấy khó trả lời nhất là \"Bạn đến từ đâu?”. Vượt lên trên một cuốn nhật ký cá nhân, Đám trẻ nhiễu văn hóa đặt ra những vấn đề nhân văn trong xã hội toàn cầu hóa đầy dịch chuyển. Mục đích cuối cùng của những suy tư và tìm tòi ấy không phải là một câu trả lời chính xác về danh tính, mà là cách để chấp nhận bản thân và tìm cho mình một nơi đề thuộc về.', '22000', 'Ngọc Linh', '272', '14x20.5 cm', '2022-06-29', '5', 'Hội nhà văn', '7', '16', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-023', 'Chùa Đàn', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2022/5/12/9YLI42GM.jpg', 'Sau một cái tử biệt, giờ ta phải tính đến một nỗi sinh li khác. Đối với đàn, hát, từ bây giờ ta nguyện làm một người điếc một người cô đơn một người phản bội. Và trên vong linh Bá Nhỡ, ta thề độc là không bao giờ cầm đến một cái chén nào của cuộc đời này.', '23000', 'Ngọc Linh A', '300', '14x20.5 cm', '2022-07-29', '5', 'Hội nhà văn', '7', '17', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-024', 'Bài thơ của một người yêu nước mình', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2020/9/4/DKQYFA75.jpg', 'Thơ của Trần Vàng Sao chính là cuộc đời ông. [...] Thơ ông hiện ra như chính áo quần ông, tóc tai ông, hơi thở ông, ánh mắt ông, giọng nói ông, cảm giác ông, mồ hôi ông, đau đớn ông, giận dữ ông, giày vò ông, tuyệt vọng ông, khát vọng ông... và nhịp đập trái tim ông là thứ kỳ diệu nhất gắn kết toàn bộ những gì thuộc về ông để vang lên thành thi ca. Bởi thế, thơ ông chân thực và mãnh liệt như máu chảy trong huyết quản ông.', '24000', 'Ngọc Linh B', '350', '14x20.5 cm', '2022-08-29', '5', 'Hội nhà văn', '7', '18', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-025', 'Muốn là nữ hoàng, đừng yêu như hầu gái', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/11/14/2QAYBLJW.jpg', 'Khi nàng biết cách làm một người đàn bà thực sự, ấy là khi nàng xứng được yêu.', '25000', 'Ngọc Linh C', '400', '14x20.5 cm', '2022-09-29', '5', 'Hội nhà văn', '7', '19', '2');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-026', 'Ký túc xá phòng 307', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/11/14/C3F24YNQ.jpg', 'Thời sinh viên, trong tôi, ý nghĩ trở thành vĩ nhân luôn thường trực. Đặc điểm chung của các vĩ nhân là họ luôn bỏ ngang đại học.', '26000', 'Ngọc Linh D', '450', '14x20.5 cm', '2022-10-29', '5', 'Hội nhà văn', '7', '20', '2');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-027', 'Người đưa thư tình', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/11/20/YB9HLB6Z.jpg', 'Người đưa thư tình chính là những mảnh vụn cuộc sống qua góc nhìn của tuổi trẻ. Mà tuổi trẻ, ấy là tuổi của những đổ vỡ và làm lại, tuổi của khát vọng và ảo tưởng, của sự nhâng nháo bất cần, của những tiếng thở dài và niềm thiết tha…', '27000', 'Ngọc Linh E', '400', '14x20.5 cm', '2022-11-29', '5', 'Hội nhà văn', '7', '21', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-028', 'Thảm kịch vĩ nhân', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/10/29/3GX3U5U3.jpg', 'Gần 600 năm trước, khi cuộc chiến chống ngoại bang kết thúc, cũng là lúc cuộc chiến phe cánh trong nội bộ triều đình nhà Lê mở ra. Trong cơn khủng hoảng đó, Nguyễn Trãi trở thành nạn nhân hứng chịu tấn thảm kịch oan khiên nhất, dã man nhất lịch sử nước Việt, mang tên Thảm kịch vĩ nhân.', '28000', 'Ngọc Linh G', '350', '14x20.5 cm', '2022-12-29', '5', 'Hội nhà văn', '7', '22', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-029', 'Về nhà', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/6/21/C5FA2DHX.jpg', 'Chiều gió mát, tôi không vào Tam Bảo mà ngồi ngoài thềm nghe đại chúng tụng kinh. Từ chỗ ngồi, tôi thấy anh thợ sơn đứng phun nhũ vàng lên các tượng Tỳ Lô Giá Na, phía sau là cây đại trổ đầy hoa đỏ và vượt qua bên ngoài tường chùa là những cành khẳng khiu của cây gạo đã rụng hết lá đang in bóng trên nền trời tráng ánh hoàng hôn. Có con chim sẻ nhỏ đậu trên đường dây điện căng ngang từ nhà tổ sang khu nhà tăng cứ líu lo một mình rất lâu rồi sà xuống sân xi măng tiếp tục líu lo, chân nhảy nhảy vui vẻ.', '29000', 'Ngọc Linh H', '300', '14x20.5 cm', '2021-06-29', '5', 'Hội nhà văn', '7', '23', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-030', 'Ly rượu trần gian', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/7/8/SN8N3FX2.jpg', 'Lần đầu tiên những trang viết ngắn của Phan Vũ được tập hợp lại để ra mắt bạn đọc. Những khắc họa chân dung cũng như những ghi chép ngắn gọn về đời sống của ông cho thấy một Phan Vũ thật khác, không chỉ là một nhà thơ, họa sĩ mà còn là một cây bút văn xuôi giàu năng lượng. Như một điều hiển nhiên, ông dành nhiều tình cảm cho Hà Nội, ông kể lại nhiều chuyện buồn vui với thành phố này, và cả câu chuyện đằng sau bài thơ đã làm làm nên cái tên Phan Vũ.', '30000', 'Ngọc Linh F', '250', '14x20.5 cm', '2021-07-29', '5', 'Hội nhà văn', '7', '24', '1');
	INSERT INTO `sprint2`.`book` (`book_code`, `book_name`, `book_image`, `book_content`, `book_price`, `book_translator`, `book_total_page`, `book_size`, `book_publish_date`, `book_quantity`, `book_publisher`, `book_category_id`, `book_author_id`, `book_promotion_id`) VALUES ('BOOK-031', 'Để con được chính-hiểu về vắc xin và miễn dịch', 'http://static.nhanam.com.vn/thumb/0x320/crop/Books/Images/2019/6/28/1A4LDY89.jpg', 'Bạn có phân vân trong việc đưa con đi tiêm chủng? Bạn có lo ngại về thông tin vắc xin MMR (sởi-rubella-quai bị) gây hội chứng tự kỷ? Và thủy ngân, cùng nhôm có thể được truyền vào cơ thể của trẻ cùng các mũi vắc xin? Con của bạn có phải là một trong hàng nghìn đứa trẻ mắc sởi vào mùa xuân 2019? Hay bé đã may mắn không mắc bệnh? Và hệ miễn dịch của trẻ, cùng của chính chúng ta thực ra hoạt động như thế nào?', '31000', 'Ngọc Linh I', '200', '14x20.5 cm', '2021-08-29', '5', 'Hội nhà văn', '7', '25', '2');


	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-001', '1', 1, '1000', '2022-01-01', '1');
	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-001', '2', 1,'4000', '2022-01-01', '1');
	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-001', '3', 1,'9000', '2022-01-01', '1');
    INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-002', '1', 1,'4000', '2022-01-01', '2');
	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-002', '2', 1,'10000', '2022-01-01', '2');
	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-002', '3', 1,'18000', '2022-01-01', '2');
	INSERT INTO `sprint2`.`cart` (`cart_code`, `cart_quantity`, `cart_status`, `cart_total_money`, `cart_purchase_date`, `cart_account_id`) VALUES ('MHD-002', '4', 1,'18000', '2022-01-01', '2');

	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('1', '1');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('2', '2');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('3', '3');
    INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('4', '4');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('5', '5');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('6', '6');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('1', '7');
    
    INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('5', '22500', b'0', '1');
	INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('4', '21600', b'0', '1');
	INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('3', '17850', b'0', '1');
	INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('2', '13600', b'0', '2');
	INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('3', '21600', b'0', '2');
	INSERT INTO `sprint2`.`cart` (`cart_quantity`, `cart_total_money`, `cart_status`, `cart_account_id`) VALUES ('4', '32000', b'0', '2');
    
    INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('5', '8');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('6', '9');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('7', '10');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('8', '11');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('9', '12');
	INSERT INTO `sprint2`.`cart_book` (`book_id`, `cart_id`) VALUES ('10', '13');








