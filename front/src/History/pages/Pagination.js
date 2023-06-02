import React from "react";
import Pagination from "react-js-pagination";
import '../css/pagination.css';


const Paging = ({page, count, setPage }) => {
    return (
      <div>
        <Pagination
          activePage={page}
          itemsCountPerPage={3}
          totalItemsCount={count}
          pageRangeDisplayed={3}
          prevPageText={"<"}
          nextPageText={">"}
          onChange={setPage}
        />
      </div>
    );
  };

  export default Paging;
  